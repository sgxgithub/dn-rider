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

        boolean invalidJson = false
        String line = ""
        String offset = ""
        String message = ""

        boolean invalidDn = false
        String content = ""
        String cont = ""

        if (schema && dn) {
            resp = JsonSchemaValidationService.validateSchema(schema, dn)

            if(resp["input2-invalid"] != null){
                invalidJson = true;
                line = resp["input2-invalid"]["line"]
                offset = resp["input2-invalid"]["offset"]
                message = resp["input2-invalid"]["message"]
            } else {
                invalidDn = resp["valid"]
                //what is the type of resp["results"] ?? why not string
                content = resp["results"]
                cont = content.replace("\\r\\n","&#13;&#10;").replace('\\"','"')
            }
        }

        respond([invalidJson:invalidJson,line:line,offset:offset,message:message,invalidDn:invalidDn, content: cont, schema: schema, dn: dn], view: 'index')
    }
}