package dn.rider

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.fge.jackson.JacksonUtils

class EditionController {

    def nexusConsumerService
    def jsonSchemaValidationService

    def index() {
        def apps = nexusConsumerService.getApps()
        [apps: apps]
    }

    def saveDn() {
        def dn = params.dn
        String app = params.app
        String releaseType = params.releaseType
        String version = params.version

        def resp = nexusConsumerService.saveDn(dn, app, releaseType, version)

        //return 405 when the target is a Maven SNAPSHOT repository
        //when the version contain 'SNAPSHOT', it will be put in the snapshots repo automatically
        if (resp.status == 400) {
            flash.error = 'This is a Maven SNAPSHOT repository, and manual upload against it is forbidden!'
            render view: 'index'
        } else {
            flash.success = "Dn Saved !" + resp.json
            render view: 'index'
        }
    }

    def validateSchema(ValidateSchemaCommand cmd) {
        String schema = jsonSchemaValidationService.getSchemaText()
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
            resp = jsonSchemaValidationService.validateSchema(schema, dn)

            if (resp["dn-invalid"] != null) {
                isJsonValid = false
                line = resp["dn-invalid"]["line"]
                offset = resp["dn-invalid"]["offset"]
                message = resp["dn-invalid"]["message"]
            } else {
                isSchemaValid = resp["valid"]
                content = resp["results"]
                cont = JacksonUtils.prettyPrint(content)
            }
        }

        respond([isChecked: isChecked, isJsonValid: isJsonValid, line: line, offset: offset, message: message, isSchemaValid: isSchemaValid, content: cont, schema: schema, dn: dn], view: 'index')
    }
}
