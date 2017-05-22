package dn.rider

import com.google.gson.JsonArray
import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

class ComparisonController {

    def nexusConsumerService

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
                dns << resp.json.NDL_pour_rundeck
            }
        }

        def packageIds
        def packages
        (packageIds, packages) = sortPackages(dns)

        respond([
                packageIds: packageIds,
                versions  : versions,
                packages  : packages,
                apps      : apps as JSON,
                app       : app,
                version1  : version1,
                version2  : version2
        ], view: "index")
    }

    def sortPackages(dns) {
        List<JsonArray> listPackages = []
        List<String> packageIds = []

        dns.eachWithIndex { dn, i ->
            //add id attribute to package
            def packages = dn.packages
            packages.each { p ->
                p.id = p.name.toString() - ('-' + p.version.toString()) + '/' + p.module
            }

            //initialise with the earliest version
            if (i == 0) {
                packages.each {
                    packageIds << it.id
                }
                listPackages << packages
            } else {
                JSONArray packagesOrderd = []
                packageIds.each { packageId ->
                    boolean isExist = packages.any { p ->
                        if (p.id == packageId) {
                            packagesOrderd.add(p)
                            packages.remove(p)
                            return true
                        }
                    }
                    if (!isExist) {
                        //how to create a JSONObject
                        //this is a linked map
                        packagesOrderd.add([
                                packageUrl: null,
                                version   : null
                        ])
                    }
                }
                if (packages) {
                    packageIds << packages.id
                    packages.each { p ->
                        p.tag = 'new'
                        packagesOrderd << p
                    }
                }
                listPackages << packagesOrderd
            }
        }

        return [packageIds, listPackages]
    }
}
