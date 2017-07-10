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

//    @Cacheable(value = 'cacheListVersions', key = '{#app, #releaseType}')
    def getVersions(String app, String releaseType) {
        log.info "Searching for the list of delivery-notes in Nexus..."
        String url
        app = app.toLowerCase()
        app = app - 'com.vsct.'
        url = "http://nexus:50080/nexus/service/local/lucene/search?g=com.vsct.${app}&a=delivery-notes"

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

//    @Cacheable(value = 'cacheListApps')
    def getApps() {
        log.info 'Searching for the apps with delivery-notes in Nexus...'
        String url = "${NEXUS_URL}service/local/lucene/search?a=delivery-notes&p=json"
        def rest = new RestBuilder()
        def resp = rest.get(url)

        NodeChildren artifacts = resp.xml.data[0].artifact
        List<String> list = []

        for (int i = 0; i < artifacts.size(); i++) {
            String groupeId = artifacts[i].groupId.toString() - 'com.vsct.'
            if (!list.contains(groupeId)) {
                list.add(groupeId)
            }
        }

        list.sort()
        return list
    }

    @Cacheable(value = 'cacheListRepos', key = '{#app, #releaseType}')
    def getRepo(String app, String releaseType) {
        log.info 'Searching for the repo in Nexus...'

        String url = "${NEXUS_URL}service/local/lucene/search?a=delivery-notes&p=json"
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

        def repoIds = artifact.artifactHits.artifactHit.repositoryId

//        def repo = repoIds.find(){ repoId ->
//            getRepoKind(repoId.toString(),
//        }

        def repo

        if (releaseType == 'releases') {
            repo = artifact?.latestReleaseRepositoryId.toString() ?: "${NEXUS_REPO_DEFAULT}-releases"
        } else {
            repo = artifact?.latestSnapshotRepositoryId.toString() ?: "${NEXUS_REPO_DEFAULT}-snapshots"
        }

        return repo
    }

    def getRepoKind(resp, String repo) {
        NodeChildren repos = resp.xml.repoDetails[0].'org.sonatype.nexus.rest.model.NexusNGRepositoryDetail'

        def repoDetail = repos.find { it ->
            it.repositoryId.toString() == repo
        }

        return repoDetail?.repositoryKind.toString()
    }

    def saveDn(dn, String app, String releaseType, String version) {
//        String repo = getRepo(app, releaseType)
        String repo = 'asset-releases'

        def f = new File('temp')
        f.append dn.bytes

        String url = "http://nexus:50080/nexus/service/local/artifact/maven/content"
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
        String url = "http://nexus:50080/nexus/service/local/repositories/asset-releases/content/com/vsct/${app}/delivery-notes/${version}/delivery-notes-${version}.json"
        def rest = new RestBuilder()
        def resp = rest.delete(url) {
            auth 'jenkins_nexus', 'Bb&fX!Z9'
        }

        return resp
    }
}