package dn.rider.consumer.nexus

import grails.plugin.cache.Cacheable
import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import groovy.util.slurpersupport.NodeChildren

@Transactional
class NexusConsumerService {

    @Cacheable(value = 'cacheDn', key = '{#app, #version}')
    def getDn(String app, String version) {
        log.info "Searching for the delivery-note in Nexus..."
        String url
        if (app.contains("com.vsct")) {
            url = "http://nexus:50080/nexus/service/local/artifact/maven/content?r=public&g=${app}&a=delivery-notes&v=${version}&p=json"
        } else url = "http://nexus:50080/nexus/service/local/artifact/maven/content?r=public&g=com.vsct.${app}&a=delivery-notes&v=${version}&p=json"
        RestBuilder rest = new RestBuilder()
        def resp = rest.get(url)

        return resp
    }

    @Cacheable(value = 'cacheListVersions', key = '{#app, #releaseType}')
    def getVersions(String app, String releaseType) {
        log.info "Searching for the list of delivery-notes in Nexus..."
        String url
        if (app.contains("com.vsct")) {
            url = "http://nexus:50080/nexus/service/local/lucene/search?g=${app}&a=delivery-notes&p=json"
        } else url = "http://nexus:50080/nexus/service/local/lucene/search?g=com.vsct.${app}&a=delivery-notes"

        RestBuilder rest = new RestBuilder()
        def resp = rest.get(url)

        //listOfVersions is of type NodeChildren
        def listOfVersions = resp.xml.data.artifact.version
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
        return list.sort()
    }

    @Cacheable(value = 'cacheListApps')
    def getApps() {
        log.info "Searching for the apps with delivery-notes in Nexus..."
        String url = "http://nexus:50080/nexus/service/local/lucene/search?a=delivery-notes&p=json"
        def rest = new RestBuilder()
        def resp = rest.get(url)

        NodeChildren artifacts = resp.xml.data[0].artifact
        List<String> list = []

        for (int i = 0; i < artifacts.size(); i++) {
            String groupeId = artifacts[i].groupId.toString() - "com.vsct."
            if (!list.contains(groupeId)) {
                list.add(groupeId)
            }
        }

        list.sort()
        //return the list of apps
        return list
    }
}