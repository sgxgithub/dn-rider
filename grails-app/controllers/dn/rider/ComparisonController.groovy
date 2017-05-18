package dn.rider

import grails.converters.JSON

class ComparisonController {

    def nexusConsumerService

    def index() {
        def apps = nexusConsumerService.getApps()
        [apps: apps as JSON]
    }

    def search() {
        String app = params.app
        String version = params.version

        log.info "searching for the delivery-note with app=${app}, version=${version}..."
        def resp = nexusConsumerService.getDn(app, version)
        log.info "received the delivery-note"

        //when there is no result
        if (resp.responseEntity.statusCode.toString() == '404') {
            String dnUrl = getNexusConsumerService().getDnUrl(app, version)
            flash.message = "No result for app=${app}, version=${version} !\nTried with url: ${dnUrl}"
            respond([
                    app    : app,
                    version: version
            ], view: "index")
            return
        }

        respond([
                packageCount: resp.json.NDL_pour_rundeck.packages.size(),
                packages    : resp.json.NDL_pour_rundeck.packages,
                app         : app,
                version     : version
        ], view: "index")
    }
}
