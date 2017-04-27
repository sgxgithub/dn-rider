package dn.rider

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode

class ValidateSchemaController {

    def JsonSchemaValidationService

    def index() {}

    def validateSchema(ValidateSchemaCommand cmd) {
        // MultipartHttpServletRequest mpr = (MultipartHttpServletRequest)request
        //CommonsMultipartFile f = (CommonsMultipartFile) mpr.getFile("fDn")
//        def f = request.getFile('fDn')
//
//        if(f.empty){
//            flash.message = "File cannot be empty"
//            render "vide"
//            return
//        }
//
//        f.transferTo(new File('/some/dic/myfile.json'))
        String schema = cmd.schema
        String dn = cmd.dn

        ObjectNode resp = JsonNodeFactory.instance.objectNode()
        boolean valid = false
        String content = ""
        //String cont = ""

        if (schema && dn) {
            resp = JsonSchemaValidationService.validateSchema(schema, dn)
            valid = resp["valid"]
            content = resp["results"]
            //cont = content.substring(1,content.length()-1).replace("\\r\\n","</br>")
            //cont = content.substring(1,content.length()-1).replace("\\r\\n","&#13;&#10;")
            //cont = content.replace("\\r\\n","<br>;")
        }

        respond([valid: valid, content: content, schema: schema, dn: dn], view: 'index')
    }
}