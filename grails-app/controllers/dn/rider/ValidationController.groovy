package dn.rider

import grails.converters.JSON
import groovy.json.JsonSlurper

class ValidationController {

    def jsonSchemaValidationService

    def uploadDn(UploadDnCommand cmd) {
        def dnFile = cmd.deliveryNoteFile

        render new String(dnFile.bytes)
    }

    def showSchema() {
        String schemaraw = jsonSchemaValidationService.getSchemaText()

        def jsonSlurper = new JsonSlurper()
        def schemaJson = jsonSlurper.parseText(schemaraw)

        respond([schemajson: schemaJson as JSON, schemaraw: schemaraw], view: 'showSchema')
    }
}