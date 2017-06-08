package dn.rider

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode

class ValidationController {

    def JsonSchemaValidationService

    //function to get schema string from a local file
    def getSchemaText(){
        def schemaPath= this.class.classLoader.getResource('NDL_katana_schema.json').path
        log.info 'Path of schema:' + schemaPath
        String schemaText = new File(schemaPath)?.getText()
        return schemaText
    }

    def index(UploadDnCommand cmd) {
        boolean isChecked = false
        String dnText = ""

        if (cmd.id) {
            Dn dn = Dn.get(cmd.id)
            dnText = new String(dn.dnBytes)
        }

        respond([dn: dnText, isChecked: isChecked], view: 'index')
    }

    def validateSchema(ValidateSchemaCommand cmd) {
        String schema = getSchemaText()
        String dn = cmd.dn

        ObjectNode resp = JsonNodeFactory.instance.objectNode()


        boolean isChecked = true  //variable to mark if there is a validation result

        boolean isJsonValid = true //variable to mark if the delivery-note satisfied format JSON
        String line = ""
        String offset = ""
        String message = ""

        boolean isSchemaValid = true //variable to mark if the delivery-note satisfied the schema
        String content
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
        String schemaText = getSchemaText()

        respond([schema: schemaText], view: 'showSchema')
    }
}