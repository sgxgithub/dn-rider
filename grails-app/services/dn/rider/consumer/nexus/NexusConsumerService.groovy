package dn.rider.consumer.nexus

import dn.rider.Dn
import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class NexusConsumerService {

    def getDnJson(String app, String version) {
        String url = "http://nexus:50080/nexus/service/local/artifact/maven/content?r=public&g=com.vsct.${app}&a=delivery-notes&v=${version}&p=json"
        RestBuilder rest = new RestBuilder()

        def response = rest.get(url)

        JSONObject json = response.json

        return json
    }

    def getDnJsonList(String app) {
        String url = "http://nexus:50080/nexus/service/local/lucene/search?g=com.vsct.${app}&a=delivery-notes&t=json"
        RestBuilder rest = new RestBuilder()

        def response = rest.get(url)

        def listVersion = response.xml.data.artifact.version

        return listVersion
    }
}
