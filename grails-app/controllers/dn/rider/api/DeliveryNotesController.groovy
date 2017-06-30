package dn.rider.api

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

        def resValidation = jsonSchemaValidationService.validateSchema(schema, dn)

        setStatus(resValidation)
        render text: resValidation.toString(), contentType: 'application/json'
    }

    def validationNoStored() {
        String dn = params.dn ?: ''
        String schema = jsonSchemaValidationService.getSchemaText()

        def res = jsonSchemaValidationService.validateSchema(schema, dn)

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

    // ref: https://support.sonatype.com/hc/en-us/articles/213465818-How-can-I-programmatically-upload-an-artifact-into-Nexus-2-
    // authentification a utiliser pour le moment : jenkins_nexus/Bb&fX!Z9
    // Prevoir un moyen de passer l’authentification dans l’appel REST entrant du DNrider.
    def saveDn() {
        def dn = params.dn
        String app = params.app
        String version = params.version
        String releaseType = params.releaseType

        String repo = nexusConsumerService.getRepo(app, releaseType)

        def f = new File('temp')
        f.append dn.bytes

        String url = "http://nexus:50080/nexus/service/local/artifact/maven/content"
        def rest = new RestBuilder()
        def resp = rest.post(url) {
            auth 'jenkins_nexus', 'Bb&fX!Z9'
            contentType "multipart/form-data"
            r = repo
            hasPom = false
            e = 'json'
            g = "com.vsct." + app
            a = 'delivery-notes'
            p = 'json'
            v = version
            file = f
        }

        f.delete()

        //when the version contain 'SNAPSHOT', it will be put in the snapshots repo automatically
        if (resp.status == 400) render status: 400, text: 'This is a Maven SNAPSHOT repository, and manual upload against it is forbidden!'
        else
            render status: 200, json: resp.json
    }

    // ref: https://stackoverflow.com/questions/34115434/how-to-delete-artifacts-with-classifier-from-nexus-using-rest-api
    // ce qu'il faut supprimer comme metadata ?
    def deleteDn() {
        String app = params.app
        String version = params.version

        String url = "http://nexus:50080/nexus/service/local/repositories/asset-releases/content/com/vsct/${app}/delivery-notes/${version}/delivery-notes-${version}.json"
//        String urlC = "http://nexus:50080/nexus/service/local/metadata/repositories/asset-releases/content"
        def rest = new RestBuilder()
        def resp = rest.delete(url) {
            auth 'jenkins_nexus', 'Bb&fX!Z9'
        }

        if (resp.status == 204) {
            render status: 200, text: 'Dn Deleted'
        } else if (resp.status == 404) {
            render status: 404, text: 'Dn Not Found'
        } else render status: 400, text: 'Failed'
    }
}