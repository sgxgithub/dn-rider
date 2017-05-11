package dn.rider

import grails.converters.JSON

class HomeController {

    def nexusConsumerService

    def index() {

        def apps = nexusConsumerService.getApps()
        [app: params.app, apps: apps as JSON]
    }

    def search(SearchVersionsCommand cmd) {
        String app = cmd.app
        if (cmd.hasErrors()) {
            flash.message = cmd.errors.allErrors.toString()
            redirect action: "index", params: [app: app]
            return
        }
        redirect controller: 'searchDn', action: 'index', params: [app: app]
    }
}
