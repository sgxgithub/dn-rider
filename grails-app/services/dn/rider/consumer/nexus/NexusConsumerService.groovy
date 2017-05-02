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
        //dn.json est de type JSONObject
        JSONObject json = response.json

        //def dn = new Dn(json)

        return json
    }
}
