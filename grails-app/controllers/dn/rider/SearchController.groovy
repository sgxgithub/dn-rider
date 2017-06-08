package dn.rider

import grails.converters.JSON

class SearchController {

    def nexusConsumerService

    def index() {
        def apps = nexusConsumerService.getApps()
        [apps: apps as JSON]
    }

    def getVersionsList() {
        String app = params.app
        String releaseType = params.releaseType

        log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
        def versions = nexusConsumerService.getVersions(app, releaseType)
        log.info "received the list of delivery-notes"

        render versions as JSON
    }

    def getVersionsView() {
        String app = params.app
        String releaseType = params.releaseType
        String template = params.template

        log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
        def versions = nexusConsumerService.getVersions(app, releaseType)
        log.info "received the list of delivery-notes"

        render template: template, model: [versions: versions, versionCount: versions.size(), app: app, releaseType: releaseType]
    }

    def search(SearchCommand cmd) {
        //take the parameters from the object command
        String app = cmd.app
        String releaseType = cmd.releaseType ?: 'all'
        String version = cmd.version

        def apps = nexusConsumerService.getApps()

        if (cmd.hasErrors()) {
            def firstError = cmd.errors.allErrors[0]
            if (firstError.field == 'app') {
                flash.message = "The valid size range of field app is between 3 and 15"
            }
            respond([
                    apps       : apps as JSON,
                    app        : app,
                    releaseType: releaseType,
                    version    : version
            ], view: 'search')
            return
        }

        //search for the list of delivery-notes by using the service functioin
        log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
        def versions = nexusConsumerService.getVersions(app, releaseType)
        log.info "received the list of delivery-notes"

        //before the user choose the version
        if (!version) {
            respond([
                    versionCount: versions.size(),
                    versions    : versions,
                    app         : app,
                    apps        : apps as JSON,
                    releaseType : releaseType
            ], view: "search")
            return
        }
        //when the user choose the version
        else {
            log.info "searching for the delivery-note with app=${app}, version=${version}..."
            def resp = nexusConsumerService.getDn(app, version)
            log.info "received the delivery-note"

            String urlNexus = getNexusConsumerService().getDnUrl(app, version)

            //when there is no result
            if (resp.responseEntity.statusCode.toString() == '404') {
                flash.message = "No result for app=${app}, version=${version} !\nTried with url: ${urlNexus}"
                respond([
                        versions    : versions,
                        versionCount: versions.size(),
                        app         : app,
                        apps        : apps as JSON,
                        releaseType : releaseType,
                        version     : version
                ], view: "index")
                return
            }

            respond([
                    versions    : versions,
                    versionCount: versions.size(),
                    dnRaw       : resp.text,
                    dnJson      : resp.json,
                    app         : app,
                    apps        : apps as JSON,
                    releaseType : releaseType,
                    version     : version,
                    urlNexus    : urlNexus
            ], view: "search")
        }
    }
}