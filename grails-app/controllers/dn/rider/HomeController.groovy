package dn.rider

import grails.converters.JSON

class HomeController {

    def nexusConsumerService
    def cookiesService

    def index() {
        flash.message = null
        //get the apps for quick access in cookie
        def appsQuickAccessArray = cookiesService.getAppsQuickAccessArray()
        String lastApp = cookiesService.getLastApp()

        [app: lastApp, appsQuickAccessArray: appsQuickAccessArray]
    }

    // autocomplete by ajax
    def getApps() {
        //get the list of apps in service
        def apps = nexusConsumerService.getApps()

        render apps as JSON
    }

    def search(SearchCommand cmd) {
        String app = cmd.app
        if (cmd.hasErrors()) {
            //flash.message = cmd.errors.allErrors.toString()
            redirect controller: 'search', action: "index", params: [app: app]
            return
        }
        redirect controller: 'search', action: 'search', params: [app: app]
    }

}
