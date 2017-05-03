package dn.rider

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.fge.jackson.JsonLoader

class ValidateSchemaController {

    def JsonSchemaValidationService

    def index(UploadDnCommand cmd) {
        boolean isChecked = false
        String dnText = ""
        String schemaText = ""
        schemaText = new File("src/main/resources/NDL_katana_schema.json").getText()

        if (cmd.id) {
            Dn dn = Dn.get(cmd.id)
            dnText = new String(dn.dnBytes)
        }

        respond([dn: dnText, schema: schemaText, isChecked: isChecked], view: 'index')
    }

    def validateSchema(ValidateSchemaCommand cmd) {
        String schema = cmd.schema
        String dn = cmd.dn

        ObjectNode resp = JsonNodeFactory.instance.objectNode()

        boolean isChecked = true
        boolean isJsonValid = true
        String line = ""
        String offset = ""
        String message = ""

        boolean isSchemaValid = true
        String content
        String cont = ""

        if (schema && dn) {
            resp = JsonSchemaValidationService.validateSchema(schema, dn)

            if (resp["input2-invalid"] != null) {
                isJsonValid = false
                line = resp["input2-invalid"]["line"]
                offset = resp["input2-invalid"]["offset"]
                message = resp["input2-invalid"]["message"]
            } else {
                isSchemaValid = resp["valid"]
                //what is the type of resp["results"] ?? why not string
                content = resp["results"]
                cont = content.replace("\\r\\n", "&#13;&#10;").replace('\\"', '"')
            }
        }

        respond([isChecked: isChecked, isJsonValid: isJsonValid, line: line, offset: offset, message: message, isSchemaValid: isSchemaValid, content: cont, schema: schema, dn: dn], view: 'index')
    }

    def uploadDn(UploadDnCommand cmd) {
        log.info "uploading dn file..."

        //save Dn in hibernate
        def dn = new Dn(dnBytes: cmd.deliveryNoteFile.bytes)
        dn.save(flush: true)
        log.info "Delivery note saved with id = ${dn.id}"

        redirect(action: "index", params: [id: dn.id])
    }

    def showSchema() {
        def url = 'http://gitlab.socrate.vsct.fr/rundep/katana/raw/dev/ndl_json-schema/NDL_katana_schema.json'
        def schema = new URL(url).getText()
    }
}