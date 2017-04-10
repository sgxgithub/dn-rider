package dn.rider.consumer.nexus

import dn.rider.nexus.Dn
import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class NexusConsumerService {

    def getDnConstante() {
        String url = "http://nexus/service/local/repositories/DWM-Releases/content/com/vsct/kli/delivery-notes/1.36.1/delivery-notes-1.36.1.json"
        //def dn = new Dn(JSON.parse(url))

        RestBuilder rest = new RestBuilder()
        def dn = rest.get(url)
        dn.json instanceof JSONObject

       // Dn dnObject = new Dn(JSON.parse(dn.json))

        return  dn.json;

    }
}
