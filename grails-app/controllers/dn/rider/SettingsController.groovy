package dn.rider

class SettingsController {

    def cookiesService

    //save the apps for quick access in cookie
    def index(SettingsCommand cmd) {

        String appsQuickAccess = cmd.appsQuickAccess

        log.info appsQuickAccess

        cookiesService.saveAppsQuickAccessArray(appsQuickAccess)

        redirect(controller: "home", action: "index")
    }
}