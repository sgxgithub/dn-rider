package dn.rider

import grails.converters.JSON
import java.util.regex.Pattern

class SearchController {

    def nexusConsumerService

    def index() {
        flash.message = null
        def apps = nexusConsumerService.getApps()
        [apps: apps as JSON]
    }

    def search(SearchCommand cmd) {
        String app = cmd.app
        String releaseType = cmd.releaseType ?: 'all'
        String version = cmd.version
        String regex = params.regex ?: ''

        def apps = nexusConsumerService.getApps()
        def versions
        def resp
        String urlNexus

        if (cmd.hasErrors()) {
            def firstError = cmd.errors.allErrors[0]
            if (firstError.field == 'app') {
                flash.message = "The valid size range of field app is between 3 and 15"
            }
        } else {
            //search for the list of delivery-notes by using the service function
            log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
            versions = nexusConsumerService.getVersions(app, releaseType)
            log.info "received the list of delivery-notes"

            //before the user choose the version
            if (version) {
                log.info "searching for the delivery-note with app=${app}, version=${version}..."
                resp = nexusConsumerService.getDn(app, version)
                log.info "received the delivery-note"

                urlNexus = getNexusConsumerService().getDnUrl(app, version)

                //when there is no result
                if (resp.responseEntity.statusCode.toString() == '404') {
                    flash.message = "No result for app=${app}, version=${version} !\nTried with url: ${urlNexus}"
                }
            }
        }
        respond([
                versions    : versions,
                versionCount: versions?.size(),
                dnRaw       : resp?.text,
                dnJson      : resp?.json,
                app         : app,
                apps        : apps as JSON,
                releaseType : releaseType,
                version     : version,
                regex       : regex,
                urlNexus    : urlNexus
        ], view: "index")
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
        String regex = params.regex ?: ''
        Pattern p = Pattern.compile(regex)
        log.info "regex: $regex"
        String template = params.template

        log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
        def versions = nexusConsumerService.getVersions(app, releaseType)
        log.info "received the list of delivery-notes"

        versions = versions.findAll { it ->
            it =~ p
        }

        render template: template, model: [versions: versions, versionCount: versions.size(), app: app, releaseType: releaseType]
    }
}