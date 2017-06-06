package dn.rider

import grails.converters.JSON
import org.grails.web.json.JSONObject

class ComparisonController {

    def nexusConsumerService
    def comparisonService

    def index() {
        def apps = nexusConsumerService.getApps()
        [apps: apps as JSON]
    }

    def compare() {
        String app = params.app
        List<String> versions = []
        versions.addAll(params.versions)

        if (!app || !versions) {
            render text: "<div></div>"
            return
        }

        List<JSONObject> dns = []
        //sort the versions
        versions.sort()

        for (int i = 0; i < versions.size(); i++) {
            String version = versions[i]
            log.info "searching for the delivery-note with app=${app}, version=${version}..."
            def resp = nexusConsumerService.getDn(app, version)

            //when there is no result for this version
            if (resp.responseEntity.statusCode.toString() == '404') {
                String dnUrl = getNexusConsumerService().getDnUrl(app, version)
                log.info "No result for app=${app}, version=${version} !\nTried with url: ${dnUrl}"
                flash.message = "No result for app=${app}, version=${version} !\nTried with url: ${dnUrl}"
            } else {
                log.info "received the delivery-note"
                def dn = resp.json.NDL_pour_rundeck
                dn.put('version', version)
                dns << dn
            }
        }

        def table = comparisonService.sortPackages(app, dns, versions)

        render template: "tableComparison", model: [rowVersions: table.rowVersions, rowPackages: table.rowPackages]
    }
}
