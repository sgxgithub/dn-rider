package dn.rider

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class DnController {

    def index() {
        String app = params.app
        String version = params.version
        String formatShow = params.formatShow
        String releaseType = params.releaseType

        respond([app: app, version: version, formatShow: formatShow, releaseType: releaseType])
    }
}