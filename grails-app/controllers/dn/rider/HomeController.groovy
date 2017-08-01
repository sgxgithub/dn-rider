package dn.rider

import grails.converters.JSON

class HomeController {

    def nexusConsumerService

    def index() {
        flash.message = null
        //get the apps for quick access in cookie
        def appsQuickAccessArray = getAppsQuickAccessArray()
        //get the list of apps in service
        def apps = nexusConsumerService.getApps()

        [apps: apps as JSON, appsQuickAccessArray:appsQuickAccessArray]
    }

    //get the apps for quick access from cookies
    def getAppsQuickAccessArray(){
        def appsQuickAccess = request.cookies.find { it.name == 'appsQuickAccess' }?.value

        if(appsQuickAccess){
            log.info "appsQuickAccess cookie found:" + appsQuickAccess
        }

       return appsQuickAccess?.tokenize('_')
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
