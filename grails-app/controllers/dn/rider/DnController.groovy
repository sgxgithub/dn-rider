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

        log.info "searching for the delivery-notes with app=${app}, version=${version}..."
        def json = nexusConsumerService.getDnJson(app, version)
        log.info "received the delivery-notes"

      /*  //dnPackage
        JSONObject jPackage = json.NDL_pour_rundeck.packages[0]
        //render dn as JSON
        def dnPackage = new DnPackage()
        dnPackage.properties = jPackage
        
        render dnPackage as JSON
       */
        // render oDn as JSON
        respond([size_packages: json.NDL_pour_rundeck.packages.size(), packages: json.NDL_pour_rundeck.packages, app:app, version:version])
    }

    def showList(){
        String app = params.app
        String releaseType = params.releaseType
        String version = params.version

        if(!app) {
            flash.message = "Fill the app name !"
        }

        log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
        def listVersion = nexusConsumerService.getDnJsonList(app,releaseType)
        log.info "received the list of delivery-notes"

        if(!version){
            respond([size_versions: listVersion.size(), listVersion: listVersion, app:app, releaseType:releaseType])
        } else {
            log.info "searching for the delivery-notes with app=${app}, version=${version}..."
            def json = nexusConsumerService.getDnJson(app, version)
            log.info "received the delivery-notes"

            respond([size_versions: listVersion.size(), listVersion: listVersion, app:app,size_packages: json.NDL_pour_rundeck.packages.size(), packages: json.NDL_pour_rundeck.packages])
        }
    }
}