package dn.rider.consumer.nexus

import grails.plugin.cache.Cacheable
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.transaction.Transactional
import groovy.util.slurpersupport.NodeChildren

@Transactional
class NexusConsumerService {

    @Cacheable(value='cacheDn', key='{#app, #version, #formatShow}')
    def getDn(String app, String version, String formatShow) {
        log.info "Searching for the delivery-note in Nexus..."
        String url = "http://nexus:50080/nexus/service/local/artifact/maven/content?r=public&g=com.vsct.${app}&a=delivery-notes&v=${version}&p=json"
        RestBuilder rest = new RestBuilder()
        def res = rest.get(url)

        //return json or text according to formatShow
        if (formatShow == "JSON") {
            return res.json
        } else {
            return res.text
        }
    }

    //@Cacheable(value='cacheListVersions', key='{#app, #releaseType}')
    def getListVersions(String app, String releaseType) {
        log.info "Searching for the list of delivery-notes in Nexus..."
        String url = "http://nexus:50080/nexus/service/local/lucene/search?g=com.vsct.${app}&a=delivery-notes&p=json"
        RestBuilder rest = new RestBuilder()
        def res = rest.get(url)

        //listOfVersions is of type NodeChildren
        def listOfVersions = res.xml.data.artifact.version
        List<String> list = []

        //when the string contains 'SNAPSHOT', consider it as Snapshot
        //add the version to list according to releaseType
        for (int i = 0; i < listOfVersions.size(); i++) {
            String version = listOfVersions[i].toString()
            if (releaseType == "Snapshots") {
                if (version.toUpperCase().contains("SNAPSHOT")) {
                    list.add(version)
                }
            } else if (releaseType == "Releases") {
                if (!version.toUpperCase().contains("SNAPSHOT")) {
                    list.add(version)
                }
            } else {
                list.add(version)
            }
        }

        //return the list of versions
        return list
    }

   // @Cacheable(value='cacheListApps')
    def getListApps() {
        log.info "Searching for the apps with delivery-notes in Nexus..."
        String url = "http://nexus:50080/nexus/service/local/lucene/search?a=delivery-notes&p=json"
        def rest = new RestBuilder()
        def resp = rest.get(url)

        NodeChildren listApps = resp.'xml'.data.artifact.groupeId
        List<String> list = []

        for (int i = 0; i < listApps.size(); i++) {
            String groupeId = listApps[i].toString()
            if (!list.contains(groupeId)) {
                    list.add(groupeId)
            }
        }

        return list
        //return the size of repositories
        //return response.xml.repoDetails.'org.sonatype.nexus.rest.model.NexusNGRepositoryDetail'.repositoryId.size()
    }
}