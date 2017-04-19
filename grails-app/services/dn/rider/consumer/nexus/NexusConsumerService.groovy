package dn.rider.consumer.nexus

import dn.rider.Dn
import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class NexusConsumerService {

    def getDn(String app, String version, String formatShow) {
        String url = "http://nexus:50080/nexus/service/local/artifact/maven/content?r=public&g=com.vsct.${app}&a=delivery-notes&v=${version}&p=json"
        RestBuilder rest = new RestBuilder()

        def response = rest.get(url)

        if (formatShow == "JSON") {
            return response.json
        } else {
            return response.text
        }
    }

    def getListVersions(String app, String releaseType) {
        String url = "http://nexus:50080/nexus/service/local/lucene/search?g=com.vsct.${app}&a=delivery-notes&t=json"
        RestBuilder rest = new RestBuilder()

        def response = rest.get(url)

        def listOfVersions = response.xml.data.artifact.version
        List<String> list = []

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

        return list

//        if(releaseType == "Snapshots"){
//            listOfVersions.each{ version ->
//                if (${version}.toString.toUpperCase().contains("SNAPSHOT")){
//
//                    listOfVersions.remove(${it})
//                }
//            }
//        } else if (releaseType == "Releases"){
//            listOfVersions.each{ version ->
//                if (!${version}.toString.toUpperCase().contains("SNAPSHOT")){
//                    //
//                    listOfVersions.remove(${it})
//                }
//            }
//        }
//        return listOfVersions
    }

    def getListApps() {
        String url = "http://nexus:50080/nexus/service/local/lucene/search?a=delivery-notes&t=json"
        RestBuilder rest = new RestBuilder()

        def response = rest.get(url)

        return response.xml.repoDetails.'org.sonatype.nexus.rest.model.NexusNGRepositoryDetail'.repositoryId.size()
    }
}
