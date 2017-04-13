package dn.rider.consumer.nexus

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class NexusConsumerService {

    def getDnJson(String app, String version) {
        String url = "http://nexus:50080/nexus/service/local/artifact/maven/content?r=public&g=com.vsct.${app}&a=delivery-notes&v=${version}&p=json"
        RestBuilder rest = new RestBuilder()

        //dn.json est de type JSONObject
        def dn = rest.get(url)

        return dn.json
    }
}
