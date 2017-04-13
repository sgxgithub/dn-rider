package dn.rider

import grails.converters.JSON

class DnController {

    def nexusConsumerService

    def index() {}

    def show() {
        String app = params.app
        String version = params.version

        log.info "searching for the delivery-notes with app=${app}, version=${version}..."

        def dn = nexusConsumerService.getDnJson(app,version)

        log.info "received the delivery-notes"

        render dn as JSON
    }
}
