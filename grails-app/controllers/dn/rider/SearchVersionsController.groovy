package dn.rider

class SearchVersionsController {

    def nexusConsumerService

    def searchVersions(SearchVersionsCommand cmd) {
        //take the parameters from the object command
        String app = cmd.app
        String version = cmd.version
        String releaseType = cmd.releaseType
        String formatShow = cmd.formatShow

        //flash message when the app name is null
        if (!app) {
            flash.message = "Fill the app name !"
            render(view: '../index')
            return
        }
        if (cmd.hasErrors()) {
            flash.message = cmd.errors.allErrors.toString()
            respond([app: app, version: version, releaseType: releaseType, formatShow: formatShow], view: 'showVersions')
            return
        }

        //search for the list of delivery-notes by using the service functioin
        log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
        def versions = nexusConsumerService.getVersions(app, releaseType)
        log.info "received the list of delivery-notes"

        //before the user choose the version
        if (!version) {
            respond([versionCount: versions.size(), versions: versions, app: app, releaseType: releaseType, formatShow: formatShow], view: "showVersions")
            return
        }
        //when the user choose the version
        else {
            log.info "searching for the delivery-note with app=${app}, version=${version}..."
            def resp = nexusConsumerService.getDn(app, version)
            log.info "received the delivery-note"

            //when there is no result
            if (resp.responseEntity.statusCode.toString() == '404') {
                String dnUrl = getNexusConsumerService().getDnUrl(app, version)
                flash.message = "No result for app=${app}, version=${version} !\nTried with url: ${dnUrl}"
                respond([version: version, versions: versions, versionCount: versions.size(), app: app, releaseType: releaseType, formatShow: formatShow], view: "showVersions")
                return
            }

            respond([version: version, versions: versions, versionCount: versions.size(), dnText: resp.text, packageCount: resp.json.NDL_pour_rundeck.packages.size(), packages: resp.json.NDL_pour_rundeck.packages, app: app, releaseType: releaseType, formatShow: formatShow], view: "showVersions")
        }
    }
}