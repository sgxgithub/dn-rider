package dn.rider

class SearchAppsController {

    def nexusConsumerService

    def index() { }

    def searchApps() {
        log.info "searching for the list of apps with delivery-notes..."
        def apps = nexusConsumerService.getApps()
        log.info "received the list of apps"

        respond([appCount: apps.size(), apps: apps], view: "showApps")
    }
}