package dn.rider.api

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import io.swagger.annotations.Api
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

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

    def saveDn() {
        def dn = params.dn
        String app = params.app
        String version = params.version
        String repo = nexusConsumerService.getRepo(app)

//        dn.transferTo(file)
//        Resource resource = new ByteArrayResource(dn.bytes)

//        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>()
//        form.add('r', 'asset-releases')
//        form.add('hasPom', false)
//        form.add('e', 'json')
//        form.add('g', 'com.vsct.xxx')
//        form.add('a', 'delivery-notes')
//        form.add('p', 'json')
//        form.add('v', '12.0')
//        form.add('file', file)

//        String url = "http://nexus:50080/nexus/service/local/artifact/maven/content"
//        def rest = new RestBuilder()
//        def resp = rest.post(url) {
//            auth 'jenkins_nexus', 'Bb&fX!Z9'
//            contentType "multipart/form-data"
////            processData = false
//            r = 'asset-releases'
//            hasPom = false
//            e = 'json'
//            g = 'com.vsct.xxx'
//            a = 'delivery-notes'
//            p = 'json'
//            v = '20.0'
//            json = new File('temp').append(dn.bytes) //can not contain \n\t
//        }

        def rest = new RestBuilder()
        String url = "http://nexus:50080/nexus/content/repositories/${repo}/com/vsct/${app}/delivery-notes/${version}/delivery-notes-${version}.json"

        def resp = rest.put(url) {
            auth 'jenkins_nexus', 'Bb&fX!Z9'
            contentType "application/json"
            json new String(dn.bytes)
        }

        render resp.text
    }
}