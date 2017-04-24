package dn.rider

class DnController {

    def nexusConsumerService

    def index() {
        String app = params.app
        String version = params.version
        String formatShow = params.formatShow
        String releaseType = params.releaseType

        respond([app: app, version: version, formatShow: formatShow, releaseType: releaseType])
    }

    def showDn() {
        //take the parameters from the variable params
        String app = params.app
        String version = params.version
        String formatShow = params.formatShow

        //flash message when there are fields null
        if (!app) {
            flash.message = "Fill the app name !"
            redirect action: "index", params:[version: version, formatShow: formatShow]
            return
        } else if (!version) {
            flash.message = "Fill the version !"
            redirect action: 'index', params:[app: app, formatShow: formatShow]
            return
        }

        //search for the delivery-note by using the service functioin
        log.info "searching for the delivery-note with app=${app}, version=${version}, format=${formatShow}..."
        def dn = nexusConsumerService.getDn(app, version, formatShow)
        log.info "received the delivery-note"

        //when there is no result
        if(dn.size()==0){
            flash.message = "No result for app=${app}, version=${version} !"
            redirect action: 'index', params:[app: app, version: version, formatShow: formatShow]
            return
        }

        //format JSON
        if (formatShow == "JSON") {
            respond([packageCount: dn.NDL_pour_rundeck.packages.size(), packages: dn.NDL_pour_rundeck.packages, app: app, version: version, formatShow: formatShow])
        }
        //format text
        else {
            respond([dnText: dn, app: app, version: version, formatShow: formatShow])
        }
    }

    def showVersions() {
        //take the parameters from the variable params
        String app = params.app
        String releaseType = params.releaseType
        String version = params.version

        //flash message when the app name is null
        if (!app) {
            flash.message = "Fill the app name !"
            redirect action: "index"
        }

        //search for the list of delivery-notes by using the service functioin
        log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
        def versions = nexusConsumerService.getVersions(app, releaseType)
        log.info "received the list of delivery-notes"

        //before the user choose the version
        if (!version) {
            respond([versionCount: versions.size(), versions: versions, app: app, releaseType: releaseType])
        }
        //when the user choose the version
        else {
            log.info "searching for the delivery-note with app=${app}, version=${version}..."
            //format Text by default
            def dn = nexusConsumerService.getDn(app, version, "Text")
            log.info "received the delivery-note"

            //when there is no result
            if(dn.size()==0){
                flash.message = "No result for app=${app}, version=${version} !"
                redirect action: 'index', params:[app: app, version: version]
                return
            }

            respond([versions: versions, versionCount: versions.size(),  dnText: dn, app: app, formatShow: "Text"])
        }
    }

    def showApps() {
        log.info "searching for the list of apps with delivery-notes..."
        def apps = nexusConsumerService.getApps()
        log.info "received the list of apps"

        respond([sizeApps: apps.size(), apps:apps])
    }
}