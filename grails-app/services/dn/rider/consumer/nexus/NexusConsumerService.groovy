package dn.rider.consumer.nexus

import grails.plugin.cache.Cacheable
import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import groovy.util.slurpersupport.NodeChildren
import org.springframework.beans.factory.annotation.Value

@Transactional
class NexusConsumerService {
    @Value('${dn.rider.nexus.url}')
    def NEXUS_URL

    @Value('${dn.rider.nexus.username}')
    def NEXUS_USERNAME

    @Value('${dn.rider.nexus.password}')
    def NEXUS_PASSWORD

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
     */
//    @Cacheable(value = 'cacheListApps')
    def getApps() {
        log.info 'Searching for the apps with delivery-notes in Nexus...'
        log.info 'Searching for the repositories in Nexus...'
        String url = "http://nexus:50080/nexus/service/local/all_repositories"

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

    //TODO: save dn with repo name, propose repo in IHM
//    @Cacheable(value = 'cacheListRepos', key = '{#app, #releaseType}')
    def getReposOfApp(String app) {
        log.info 'Searching for the repo in Nexus...'

        String url = "${NEXUS_URL}service/local/lucene/search?g=com.vsct.${app}&a=delivery-notes&p=json"
        def rest = new RestBuilder()
        def resp = rest.get(url)

        NodeChildren artifacts = resp.xml.data[0].artifact

        // recherche un repo
        def artifact = artifacts.find() { artifact ->
            String groupeId = artifact.groupId.toString() - 'com.vsct.'

            if (groupeId.contains(app.toLowerCase()) && repoKind == 'hosted') {
                return true
            }
        }

        def repoId = artifact.artifactHits.artifactHit.repositoryId
    }

    def saveDn(dn, String app, String releaseType, String version) {
        String repo = 'asset-releases'

        def f = new File('temp')
        f.append dn.bytes

        String url = "${NEXUS_URL}service/local/artifact/maven/content"
        def rest = new RestBuilder()
        def resp = rest.post(url) {
            auth 'jenkins_nexus', 'Bb&fX!Z9'
            contentType "multipart/form-data"
            r = repo
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
            auth 'jenkins_nexus', 'Bb&fX!Z9'
        }

        return resp
    }
}