package dn.rider.api

import grails.converters.JSON

class DeliveryNotesController {

    def nexusConsumerService

    def show() {
        String app = params.app
        //def releaseType = 'all'

        log.info "searching for the list of delivery-notes with app=${app}"//, releaseType=${releaseType}..."
        def versions = nexusConsumerService.getVersions(app)
        log.info "received the list of delivery-notes"

        render versions as JSON
    }
}