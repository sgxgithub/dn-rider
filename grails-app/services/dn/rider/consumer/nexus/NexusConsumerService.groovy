package dn.rider.consumer.nexus

import grails.transaction.Transactional

@Transactional
class NexusConsumerService {

    def getUrlExample() {
        return "http://nexus/service/local/repositories/DWM-Releases/content/com/vsct/kli/delivery-notes/1.36.1/delivery-notes-1.36.1.json"
    }
}
