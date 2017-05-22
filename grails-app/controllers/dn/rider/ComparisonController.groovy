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
        String version1 = params.version1
        String version2 = params.version2

        List<String> versions = [version1, version2]
        def resps = []
        def packages = [] //a list of packages

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
                resps.add(resp)
                packages.add(resp.json.NDL_pour_rundeck.packages)
            }
        }

        def packageNames = getPackageNames(resps)

        respond([
                packageNames: packageNames,
                versions    : versions,
                packages    : packages,
                apps        : apps as JSON,
                app         : app,
                version1    : version1,
                version2    : version2
        ], view: "index")
    }

    def getPackageNames(resps) {
        List<String> list = []

        for (int i = 0; i < resps.size(); i++) {
            def packages = resps[i].json.NDL_pour_rundeck.packages

            for (int j = 0; j < packages.size(); j++) {
                String name = packages[j].name.toString() - ('-' + packages[j].version.toString())
                if (!list.contains(name)) {
                    list.add(name)
                }
            }
        }
        return list
    }
}
