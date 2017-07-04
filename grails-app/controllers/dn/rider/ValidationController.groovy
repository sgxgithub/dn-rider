package dn.rider

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.fge.jackson.JacksonUtils
import grails.converters.JSON
import groovy.json.JsonSlurper

class ValidationController {

    def nexusConsumerService
    def JsonSchemaValidationService

    def index() {
        flash.message = null
        boolean isChecked = false

        String dn = params.dn
        respond([dn: dn, isChecked: isChecked], view: 'index')
    }

    def validationDnFromNexus() {
        String app = params.app
        String version = params.version

        log.info "searching for the delivery-note with app=${app}, version=${version}..."
        def resp = nexusConsumerService.getDn(app, version)
        log.info "received the delivery-note"

        String dn = resp.text
        boolean isChecked = false

        respond([dn: dn, isChecked: isChecked], view: 'index')
    }

    def validateSchema(ValidateSchemaCommand cmd) {
        String schema = JsonSchemaValidationService.getSchemaText()
        String dn = cmd.dn

        ObjectNode resp = JsonNodeFactory.instance.objectNode()

        boolean isChecked = true  //variable to mark if there is a validation result

        boolean isJsonValid = true //variable to mark if the delivery-note satisfied format JSON
        String line = ""
        String offset = ""
        String message = ""

        boolean isSchemaValid = true //variable to mark if the delivery-note satisfied the schema
        JsonNode content
        String cont = ""

        if (schema && dn) {
            resp = JsonSchemaValidationService.validateSchema(schema, dn)

            if (resp["dn-invalid"] != null) {
                isJsonValid = false
                line = resp["dn-invalid"]["line"]
                offset = resp["dn-invalid"]["offset"]
                message = resp["dn-invalid"]["message"]
            } else {
                isSchemaValid = resp["valid"]
                content = resp["results"]
                cont = JacksonUtils.prettyPrint(content)
//                cont = content.replace("\\r\\n", "&#13;&#10;").replace('\\"', '"')
            }
        }

        respond([isChecked: isChecked, isJsonValid: isJsonValid, line: line, offset: offset, message: message, isSchemaValid: isSchemaValid, content: cont, schema: schema, dn: dn], view: 'index')
    }

    def uploadDn(UploadDnCommand cmd) {
        def dnFile = cmd.deliveryNoteFile

        render new String(dnFile.bytes)
    }

    def showSchema() {
        String schemaraw = JsonSchemaValidationService.getSchemaText()

        def jsonSlurper = new JsonSlurper()
        def schemaJson = jsonSlurper.parseText(schemaraw)

        respond([schemajson: schemaJson as JSON, schemaraw: schemaraw], view: 'showSchema')
    }
}