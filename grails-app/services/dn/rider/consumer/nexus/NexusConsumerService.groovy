package dn.rider.consumer.nexus

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional

@Transactional
class NexusConsumerService {

    def getDnConstante() {
        String url = "http://nexus/service/local/repositories/DWM-Releases/content/com/vsct/kli/delivery-notes/1.36.1/delivery-notes-1.36.1.json"
        //def dn = new Dn(JSON.parse(url))

        RestBuilder rest = new RestBuilder()
        def dn = rest.get(url)
        //dn.json instanceof JSONObject

        //def dnObject = new Dn(JSON.parse(dn.json))
        return  dn.json;
        //return dnObject;
    }
}
