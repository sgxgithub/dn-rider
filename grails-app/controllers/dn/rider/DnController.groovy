package dn.rider

import grails.converters.JSON

class DnController {

    def nexusConsumerService

    def index() {}

    def show() {
        //String app =  "DWM-Releases"
        //String version = "1.36.1"
        String app = params.app
        String version = params.version
        def dn = nexusConsumerService.getDnJson(app,version)
        //def dn = new Dn(nexusConsumerService.getDnJSon())

        render dn as JSON
    }
}
