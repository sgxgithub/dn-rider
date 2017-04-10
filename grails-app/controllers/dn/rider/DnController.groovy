package dn.rider

import grails.converters.JSON

class DnController {

    def nexusConsumerService

    def index() {}

    def show() {
        def dn = nexusConsumerService.getDnConstante()

        //render dn as JSON
        render dn
    }
}
