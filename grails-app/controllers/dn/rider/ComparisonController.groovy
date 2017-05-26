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

    def search() {
        String app = params.app
        String version1 = params.version1
        String version2 = params.version2

        List<String> versions = [version1, version2]
        List<JSONObject> dns = []

        def apps = nexusConsumerService.getApps()

        //sort the versions
        //code here

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

//        def packageKeys
//        def listPackages
//        (packageKeys, listPackages) = comparisonService.sortPackages(dns)
        def rowPackages = comparisonService.sortPackages(dns, versions)

        respond([
                rowPackages: rowPackages,
                versions   : versions,
//                packageKeys: packageKeys,
//                listPackages  : listPackages,
                apps       : apps as JSON,
                app        : app,
                version1   : version1,
                version2   : version2
        ], view: "index")
    }
}
