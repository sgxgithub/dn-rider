package dn.rider

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class DnController {

    def nexusConsumerService
    def JsonSchemaValidationService

    def index() {
        String app = params.app
        String version = params.version
        String formatShow = params.formatShow
        String releaseType = params.releaseType

        respond([app: app, version: version, formatShow: formatShow, releaseType: releaseType])
    }

    def showApps() {
        log.info "searching for the list of apps with delivery-notes..."
        def apps = nexusConsumerService.getApps()
        log.info "received the list of apps"

        respond([appCount: apps.size(), apps: apps])
    }

    def validateSchema() {
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
        String schema = params.schema
        String dn = params.dn

        ObjectNode resp = JsonNodeFactory.instance.objectNode()
        boolean valid = false
        String content = ""
        //String cont

        if (schema && dn) {
            resp = JsonSchemaValidationService.validateSchema(schema, dn)
            valid = resp["valid"]
            content = resp["results"]
            //cont = content.substring(1,content.length()-1).replace("\\r\\n","&#13;&#10;")
        }

        respond([valid: valid, content: content, schema: schema, dn: dn])
    }
}