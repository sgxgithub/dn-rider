package com.vsct.dnrider.consumer

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional

@Transactional
class GoogleConsumerService {

    def search() {
        RestBuilder rest = new RestBuilder()
        def googleSearch = rest.get("https://www.google.com?q=myLittlePoney")
        log.info 'response from google', googleSearch
        googleSearch
    }
}
