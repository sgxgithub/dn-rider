package dn.rider

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile



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
            redirect action: "index", params: [version: version, formatShow: formatShow]
            return
        } else if (!version) {
            flash.message = "Fill the version !"
            redirect action: 'index', params: [app: app, formatShow: formatShow]
            return
        }

        //search for the delivery-note by using the service functioin
        log.info "searching for the delivery-note with app=${app}, version=${version}, format=${formatShow}..."
        def resp = nexusConsumerService.getDn(app, version)
        log.info "received the delivery-note"

        //when there is no result
        if (resp.responseEntity.statusCode.toString() == "404") {
            flash.message = "No result for app=${app}, version=${version} !"
            redirect action: 'index', params: [app: app, version: version, formatShow: formatShow]
            return
        }

        //format JSON
        if (formatShow == "JSON") {
            respond([packageCount: resp.json.NDL_pour_rundeck.packages.size(), packages: resp.json.NDL_pour_rundeck.packages, app: app, version: version, formatShow: formatShow],view:"showApps")
        }
        //format text
        else {
            respond([dnText: resp.text, app: app, version: version, formatShow: formatShow],view:"showApps")
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
            respond([versionCount: versions.size(), versions: versions, app: app, releaseType: releaseType], view:"showApps")
        }
        //when the user choose the version
        else {
            log.info "searching for the delivery-note with app=${app}, version=${version}..."
            def resp = nexusConsumerService.getDn(app, version)
            log.info "received the delivery-note"

            //when there is no result
            if (resp.responseEntity.statusCode.toString() == "404") {
                flash.message = "No result for app=${app}, version=${version} !"
                redirect action: 'index', params: [app: app, version: version]
                return
            }

            //format Text by default
            respond([versions: versions, versionCount: versions.size(), dnText: resp.text, app: app, formatShow: "Text"], view:"showApps")
        }
    }

    def showApps() {
        log.info "searching for the list of apps with delivery-notes..."
        def apps = nexusConsumerService.getApps()
        log.info "received the list of apps"

        respond([appCount: apps.size(), apps: apps])
    }

    def validation() {
       // MultipartHttpServletRequest mpr = (MultipartHttpServletRequest)request
        //CommonsMultipartFile f = (CommonsMultipartFile) mpr.getFile("fDn")
//        def f = request.getFile('fDn')
//
//        if(f.empty){
//            flash.message = "File cannot be empty"
//            render "vide"
//            return
//        }
//
//        f.transferTo(new File('/some/dic/myfile.json'))
//        respond()
    }
}