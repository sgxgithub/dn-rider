package dn.rider

class SearchDnController {

    def nexusConsumerService

    def index() {}

    def searchDn(SearchDnCommand cmd) {

        //take the parameters from the object command
        String app = cmd.app
        String version = cmd.version
        String formatShow = cmd.formatShow

        //flash message when there are fields null
        if (!app) {
            flash.message = "Fill the app name !"
            redirect action: "index", params: [version: version, formatShow: formatShow]
            return
        } else if (!version) {
            flash.message = "Fill the version !"
            redirect action: 'index', params: [app: app, formatShow: formatShow]
            return
        } else if (cmd.hasErrors()) {
//            if (cmd.errors.hasFieldErrors("app")) {
//                flash.message =  cmd.errors.getFieldError("app").rejectedValue
//            } else if (cmd.errors.hasFieldErrors("version")) {
//                flash.message =  cmd.errors.getFieldError("version").rejectedValue
//            }
            flash.message = cmd.errors.allErrors.toString()
            redirect action: 'index', params: [version: version, formatShow: formatShow]
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
            respond([packageCount: resp.json.NDL_pour_rundeck.packages.size(), packages: resp.json.NDL_pour_rundeck.packages, app: app, version: version, formatShow: formatShow], view: "showDn")
        }
        //format text
        else {
            respond([dnText: resp.text, app: app, version: version, formatShow: formatShow], view: "showDn")
        }
    }
}
