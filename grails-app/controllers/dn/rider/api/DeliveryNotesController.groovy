package dn.rider.api

import com.fasterxml.jackson.databind.node.ObjectNode
import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import io.swagger.annotations.Api

@Api(value = "DeliveryNotesController")
class DeliveryNotesController {

    def nexusConsumerService
    def jsonSchemaValidationService

    def showApps() {
        String format = params.format ?: 'json'

        def apps = nexusConsumerService.getApps()

        if (format.toUpperCase() == 'TEXT')
            render apps
        else render apps as JSON
    }

    def showVersions() {
        String app = params.app
        String releaseType = params.releaseType ?: 'all'
        String format = params.format ?: 'json'

        log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
        def versions = nexusConsumerService.getVersions(app, releaseType)
        log.info "received the list of delivery-notes"

        if (format.toUpperCase() == 'TEXT')
            render versions.join(' ')
        else render versions as JSON
    }

    def showDn() {
        String app = params.app
        String version = params.version
        String format = params.format ?: 'json'

        log.info "searching for the delivery-note with app=${app}, version=${version}..."
        def resp = nexusConsumerService.getDn(app, version)
        log.info "received the delivery-note"

        //when there is no result
        if (resp.responseEntity.statusCode.toString() == '404') {
            String dnUrl = getNexusConsumerService().getDnUrl(app, version)
            String message = "No result for app=${app}, version=${version} !\nTried with url: ${dnUrl}"
            render message
            return
        }

        if (format.toUpperCase() == 'TEXT')
            render resp.text
        else render resp.json
    }

    def validationStored() {
        String app = params.app
        String version = params.version

        log.info "searching for the delivery-note with app=${app}, version=${version}..."
        def resp = nexusConsumerService.getDn(app, version)
        log.info "received the delivery-note"

        //when there is no result
        if (resp.responseEntity.statusCode.toString() == '404') {
            String dnUrl = getNexusConsumerService().getDnUrl(app, version)
            response.status = 404
            String message = "No result for app=${app}, version=${version} !\nTried with url: ${dnUrl}"
            render message
            return
        }

        String dn = resp.text
        String schema = jsonSchemaValidationService.getSchemaText()

        ObjectNode resValidation = jsonSchemaValidationService.validateSchema(schema, dn)

        setStatus(resValidation)
        render text: resValidation.toString(), contentType: 'application/json'
    }

    def validationNoStored() {
        String dn = params.dn ?: ''
        String schema = jsonSchemaValidationService.getSchemaText()

        ObjectNode res = jsonSchemaValidationService.validateSchema(schema, dn)

        setStatus(res)
        render text: res.toString(), contentType: 'application/json'
    }

    def setStatus(res) {
        if (res['valid']) response.status = 200
        else {
            if (!res['valid']) response.status = 422
            else response.status = 500
        }
    }

    def saveDn(){
        def dn = params.dn
        String url = "http://nexus:50080/nexus//content/repositories/asset-releases/com/vsct/xxx/foo-2.0.json"

        def rest = new RestBuilder()
        def resp = rest.post(url) {
            auth 'jenkins_nexus', 'Bb&fX!Z9'
            contentType "multipart/form-data"
//            json = dn
        }

        render true

    }
}