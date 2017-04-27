package dn.rider

class DnController {

    def index() {
        String app = params.app
        String version = params.version
        String formatShow = params.formatShow
        String releaseType = params.releaseType

        respond([app: app, version: version, formatShow: formatShow, releaseType: releaseType])
    }
}