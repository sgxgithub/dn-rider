package dn.rider.api

import grails.converters.JSON

class DeliveryNotesController {

    def nexusConsumerService

    def show() {
        String app = params.app
        String releaseType = params.releaseType

        log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
        def versions = nexusConsumerService.getVersions(app, releaseType)
        log.info "received the list of delivery-notes"

        render versions as JSON
    }
}