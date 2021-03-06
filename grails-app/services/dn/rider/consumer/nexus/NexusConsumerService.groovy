package dn.rider.consumer.nexus

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import groovy.util.slurpersupport.NodeChildren
import org.grails.web.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import grails.plugin.cache.*
import grails.plugin.cache.GrailsCacheManager

import java.util.regex.Pattern

@Transactional
class NexusConsumerService {
    @Value('${dn.rider.nexus.url}')
    def NEXUS_URL

    @Value('${dn.rider.nexus.username}')
    String NEXUS_USERNAME

    @Value('${dn.rider.nexus.password}')
    String NEXUS_PASSWORD

    @Value('${dn.rider.nexus.repoDefault}')
    def NEXUS_REPO_DEFAULT

    GrailsCacheManager grailsCacheManager

    def getDnUrl(String app, String version) {
        String url
        app = app.toLowerCase()
        app = app - 'com.vsct.'
        url = "${NEXUS_URL}service/local/artifact/maven/content?r=public&g=com.vsct.${app}&a=delivery-notes&v=${version}&p=json"
        return url
    }

    @Cacheable(value = 'dn', key = '{#app, #version}')
    def getDn(String app, String version) {
        log.info 'Searching for the delivery-note in Nexus...'
        String url = getDnUrl(app, version)
        RestBuilder rest = new RestBuilder()
        def resp = rest.get(url)

        return resp
    }

    def getPackages(String app, String version) {
        log.info 'Searching for the delivery-note in Nexus...'
        String url = getDnUrl(app, version)
        RestBuilder rest = new RestBuilder()
        def resp = rest.get(url)

        def packages = resp.json.NDL_pour_rundeck.packages

        List<String> list = []

        packages.each { p ->
            String id = p.name + '#' + p.module
            list.add(id)
        }

        return list
    }

    @Cacheable(value = 'versions', key = '{#app}')
    def getVersions(String app) {
        log.error "Searching for the list of delivery-notes in Nexus with app=${app}..."
        String url
        app = app.toLowerCase() - 'com.vsct.'
        url = "${NEXUS_URL}service/local/lucene/search?g=com.vsct.${app}&a=delivery-notes"

        RestBuilder rest = new RestBuilder()
        def resp = rest.get(url)

        //listOfVersions is of type NodeChildren
        def versions = resp.xml.data.artifact.version

        List<String> list = []
        versions.each {
            list.add(it.toString())
        }

        //filter form newest to oldest
        list.reverse()

        //return the list of versions
        return list
    }

    def filterVersions(List<String> versions, String releaseType, String regex = '') {
        log.info "regex: $regex"
        Pattern p = Pattern.compile(regex)
        versions = versions.findAll { it ->
            it =~ p
        }

        if (releaseType.toUpperCase() == 'SNAPSHOTS') {
            return versions.findAll { version ->
                version.toUpperCase().contains("SNAPSHOT")
            }
        } else if (releaseType.toUpperCase() == 'RELEASES') {
            return versions.findAll { version ->
                !version.toUpperCase().contains("SNAPSHOT")
            }
        } else return versions
    }

    /**
     * step 1 : find all the repositories in Nexus
     * step 2 : add all the apps in every repositories in the list, remove the duplicates and sort the list
     * limitation in Nexus 2, if we use ${NEXUS_URL}service/local/lucene/search?a=delivery-notes&p=json to search all the apps, Lucene doesn't return all the results(too many results)
     * Nexus issue : https://issues.sonatype.org/browse/NEXUS-8365
     */
    @Cacheable(value = 'apps')
    def getApps() {
        log.info 'Searching for the apps with delivery-notes in Nexus...'
        log.info 'Searching for the repositories in Nexus...'
        String url = "${NEXUS_URL}service/local/all_repositories"

        def rest = new RestBuilder()
        def resp = rest.get(url)

        NodeChildren repoIds = resp.xml.data[0]['repositories-item'].id
        List<String> list = []

        repoIds.each { repoId ->
            def repoApps = getAppsInRepository(repoId.toString())
            list.addAll(repoApps)
        }

        list.unique().sort()
        return list
    }

    def getAppsInRepository(String repositoryId) {
        log.info "Searching for the apps in repository ${repositoryId} in Nexus..."
        String url = "${NEXUS_URL}service/local/lucene/search?a=delivery-notes&p=json&repositoryId=${repositoryId}"
        def rest = new RestBuilder()
        def resp = rest.get(url)

        NodeChildren artifacts = resp.xml.data[0].artifact
        List<String> list = []

        for (int i = 0; i < artifacts.size(); i++) {
            String groupeId = artifacts[i].groupId.toString() - 'com.vsct.'
            list.add(groupeId)
        }

        list.unique().sort()
        return list
    }

    @Cacheable(value = 'repositoryIds', key = '{#app, #releaseType, #version}')
    def getRepositoryIds(String app, String releaseType = 'all', String version = '') {
        log.info 'Searching for the repositoryIds in Nexus...'

        String url = "${NEXUS_URL}service/local/lucene/search?g=com.vsct.${app}&v=${version}&a=delivery-notes&p=json"
        def rest = new RestBuilder()
        def resp = rest.get(url)

        def repositories = resp.xml.repoDetails[0]['org.sonatype.nexus.rest.model.NexusNGRepositoryDetail']

        def list = []
        repositories.each { repository ->
            // use only the repositoryId with repositoryKind = hosted
            if (repository.repositoryKind.toString().toUpperCase() == 'HOSTED') {
                if (releaseType == 'releases') {
                    if (repository.repositoryPolicy.toString() == 'RELEASE')
                        list.add(repository.repositoryId.toString())
                } else if (releaseType == 'snapshots') {
                    if (repository.repositoryPolicy.toString() == 'SNAPSHOT')
                        list.add(repository.repositoryId.toString())
                } else list.add(repository.repositoryId.toString())
            }
        }

        list.unique()

        return list
    }

    @Cacheable(value = 'repositoryPolicy', key = '{#app, #repositoryId}')
    def getRepositoryPolicy(String repositoryId) {
        log.info "Searching for the repositoryPolicy with repositoryId = ${repositoryId}"

        String url = "${NEXUS_URL}service/local/lucene/search?a=delivery-notes&p=json&repositoryId=${repositoryId}"
        def rest = new RestBuilder()
        def resp = rest.get(url)

        //bad request with this repositoryId
        if (resp.responseEntity.statusCode.toString() == '400') return null

        def repoDetails = resp.xml?.repoDetails[0]['org.sonatype.nexus.rest.model.NexusNGRepositoryDetail'][0]

        return repoDetails?.repositoryPolicy?.toString()
    }

    @CacheEvict(value = 'versions', key = '{#app}')
    def saveDn(dn, String app, String version, String repositoryId) {
        def f = new File('temp')
        f.append dn.bytes

        String url = "${NEXUS_URL}service/local/artifact/maven/content"
        def rest = new RestBuilder()
        def resp = rest.post(url) {
            auth NEXUS_USERNAME, NEXUS_PASSWORD
            contentType "multipart/form-data"
            r = repositoryId
            hasPom = false
            e = 'json'
            g = "com.vsct." + app
            a = 'delivery-notes'
            p = 'json'
            v = version
            file = f
        }

        f.delete()

        List<String> apps = getApps()
        List<String> versions = getVersions(app)
        if (!apps.any { it == app }) { //delete cache of the apps (in case when add new app)
            grailsCacheManager.getCache('apps').clear()
        }
        if (versions.any { it == version }) { //delete cache of the dn with (app, version) as key (in case of renew)
            grailsCacheManager.getCache('dn').evict([app, version])
        }

        return resp
    }

    @CacheEvict(value = 'versions', key = '{#app}')
    def deleteDn(String app, String version, String repositoryId) {
        String url = "${NEXUS_URL}service/local/repositories/${repositoryId}/content/com/vsct/${app}/delivery-notes/${version}/delivery-notes-${version}.json"
        def rest = new RestBuilder()
        def resp = rest.delete(url) {
            auth NEXUS_USERNAME, NEXUS_PASSWORD
        }

        //delete cache of the dn with (app, version) as key
        grailsCacheManager.getCache('dn').evict([app, version])

        return resp
    }
}