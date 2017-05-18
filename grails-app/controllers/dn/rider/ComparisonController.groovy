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
        String version2 = params.version2

        def apps = nexusConsumerService.getApps()

        log.info "searching for the delivery-note with app=${app}, version=${version}..."
        def resp = nexusConsumerService.getDn(app, version)
        log.info "received the delivery-note"

        log.info "searching for the delivery-note with app=${app}, version=${version2}..."
        def resp2 = nexusConsumerService.getDn(app, version2)
        log.info "received the delivery-note"

        //when there is no result
        if (resp.responseEntity.statusCode.toString() == '404') {
            String dnUrl = getNexusConsumerService().getDnUrl(app, version)
            flash.message = "No result for app=${app}, version=${version} !\nTried with url: ${dnUrl}"
            respond([
                    apps   : apps as JSON,
                    app    : app,
                    version: version
            ], view: "index")
            return
        }

        respond([
                packageCount: resp.json.NDL_pour_rundeck.packages.size(),
                packages    : resp.json.NDL_pour_rundeck.packages,
                packages2   : resp2.json.NDL_pour_rundeck.packages,
                apps        : apps as JSON,
                app         : app,
                version     : version,
                version2    : version2
        ], view: "index")
    }
}
