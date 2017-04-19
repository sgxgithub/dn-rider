package dn.rider

import dn.rider.content.DnPackage
import grails.converters.JSON
import grails.databinding.DataBinder
import org.grails.web.json.JSONObject

class DnController {

    def nexusConsumerService

    def index() {}

    def show() {
        String app = params.app
        String version = params.version
        String formatShow = params.formatShow

        log.info "searching for the delivery-note with app=${app}, version=${version}, format=${formatShow}..."
        def dn = nexusConsumerService.getDn(app, version, formatShow)
        log.info "received the delivery-notes"

        /*  //dnPackage
          JSONObject jPackage = json.NDL_pour_rundeck.packages[0]
          //render dn as JSON
          def dnPackage = new DnPackage()
          dnPackage.properties = jPackage

          render dnPackage as JSON
         */
        //format JSON
        if (formatShow == "JSON") {
            respond([size_packages: dn.NDL_pour_rundeck.packages.size(), packages: dn.NDL_pour_rundeck.packages, app: app, version: version, formatShow: formatShow])
            //respond([dn: dn, app: app, version: version, formatShow: formatShow])
        }
        //format text
        else {
            respond([dnText: dn, app: app, version: version, formatShow: formatShow])
        }
    }

    def showList() {
        String app = params.app
        String releaseType = params.releaseType
        String version = params.version

        if (!app) {
            flash.message = "Fill the app name !"
        }

        log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
        def listVersion = nexusConsumerService.getListVersions(app, releaseType)
        log.info "received the list of delivery-notes"

        if (!version) {
            respond([size_versions: listVersion.size(), listVersion: listVersion, app: app, releaseType: releaseType])
        } else {
            log.info "searching for the delivery-note with app=${app}, version=${version}..."
            //format JSON by default
            def dn = nexusConsumerService.getDn(app, version, "JSON")
            log.info "received the delivery-notes"

            respond([size_versions: listVersion.size(), listVersion: listVersion, size_packages: dn.NDL_pour_rundeck.packages.size(), packages: dn.NDL_pour_rundeck.packages, app: app, formatShow: "JSON"])
        }
    }

    def showApps(){
        log.info "searching for the list of apps with delivery-notes..."
        def sizeApps = nexusConsumerService.getListApps()
        log.info "received the list of apps"

        respond([sizeApps:sizeApps])
    }
}