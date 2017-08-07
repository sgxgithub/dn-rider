package dn.rider.consumer.nexus

import grails.plugin.cache.Cacheable
import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import groovy.util.slurpersupport.NodeChildren
import org.grails.web.json.JSONObject
import org.springframework.beans.factory.annotation.Value

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

    def getDnUrl(String app, String version) {
        String url
        app = app.toLowerCase()
        app = app - 'com.vsct.'
        url = "${NEXUS_URL}service/local/artifact/maven/content?r=public&g=com.vsct.${app}&a=delivery-notes&v=${version}&p=json"
        return url
    }

    // @Cacheable(value = 'cacheDn', key = '{#app, #version}')
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

//    @Cacheable(value = 'cacheListVersions', key = '{#app, #releaseType}')
    def getVersions(String app, String releaseType) {
        log.info "Searching for the list of delivery-notes in Nexus..."
        String url
        app = app.toLowerCase()
        app = app - 'com.vsct.'
        url = "${NEXUS_URL}service/local/lucene/search?g=com.vsct.${app}&a=delivery-notes"

        RestBuilder rest = new RestBuilder()
        def resp = rest.get(url)

        //listOfVersions is of type NodeChildren
        def listOfVersions = resp.xml.data.artifact.version
        List<String> list = []

        //when the string contains 'SNAPSHOT', consider it as Snapshot
        //add the version to list according to releaseType
        for (int i = 0; i < listOfVersions.size(); i++) {
            String version = listOfVersions[i].toString()
            if (releaseType.toUpperCase() == 'SNAPSHOTS') {
                if (version.toUpperCase().contains("SNAPSHOT")) {
                    list.add(version)
                }
            } else if (releaseType.toUpperCase() == 'RELEASES') {
                if (!version.toUpperCase().contains("SNAPSHOT")) {
                    list.add(version)
                }
            } else {
                list.add(version)
            }
        }
        //filter form newest to oldest
        list.reverse()

        //return the list of versions
        return list
    }

    /**
     * step 1 : find all the repositories in Nexus
     * step 2 : add all the apps in every repositories in the list, remove the duplicates and sort the list
     * limitation in Nexus 2, if we use ${NEXUS_URL}service/local/lucene/search?a=delivery-notes&p=json to search all the apps, Lucene doesn't return all the results(too many results)
     * Nexus issue : https://issues.sonatype.org/browse/NEXUS-8365
     */
//    @Cacheable(value = 'cacheListApps')
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

//    @Cacheable(value = 'cacheListApps', key = '{#repositoryId}')
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

    def getRepositoryIds(String app, String releaseType = 'all') {
        log.info 'Searching for the repositoryIds in Nexus...'

        String url = "${NEXUS_URL}service/local/lucene/search?g=com.vsct.${app}&a=delivery-notes&p=json"
        def rest = new RestBuilder()
        def resp = rest.get(url)

        def repositories = resp.xml.repoDetails[0]['org.sonatype.nexus.rest.model.NexusNGRepositoryDetail']

        def list = []
        repositories.each { repository ->
            if (releaseType == 'releases') {
                if (repository.repositoryPolicy.toString() == 'RELEASE')
                    list.add(repository.repositoryId.toString())
            } else if (releaseType == 'snapshots') {
                if (repository.repositoryPolicy.toString() == 'SNAPSHOT')
                    list.add(repository.repositoryId.toString())
            } else list.add(repository.repositoryId.toString())
        }

        list.unique()

        return list
    }

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

//    @Cacheable(value = 'cacheListRepos', key = '{#app, #releaseType}')
    def getRepoIdsAndNames(String app) {
        log.info 'Searching for the repo in Nexus...'

        String url = "${NEXUS_URL}service/local/lucene/search?g=com.vsct.${app}&a=delivery-notes&p=json"
        def rest = new RestBuilder()
        def resp = rest.get(url)

        NodeChildren repoDetails = resp.xml.repoDetails[0]['org.sonatype.nexus.rest.model.NexusNGRepositoryDetail']

        JSONObject repos = new JSONObject()
        repoDetails.each { repoDetail ->
            repos.put(repoDetail.repositoryId.toString(), repoDetail.repositoryName.toString())
        }

        return repos
    }

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

        return resp
    }

    def deleteDn(String app, String version) {
        String url = "${NEXUS_URL}service/local/repositories/asset-releases/content/com/vsct/${app}/delivery-notes/${version}/delivery-notes-${version}.json"
        def rest = new RestBuilder()
        def resp = rest.delete(url) {
            auth NEXUS_USERNAME, NEXUS_PASSWORD
        }

        return resp
    }
}