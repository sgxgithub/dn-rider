package dn.rider.consumer.nexus

import dn.rider.nexus.Dn
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class NexusConsumerService {

    def getDnConstante() {
        String url = "http://nexus/service/local/repositories/DWM-Releases/content/com/vsct/kli/delivery-notes/1.36.1/delivery-notes-1.36.1.json"
        def dn = new Dn(JSON.parse(url))

        return dn;
    }
}
