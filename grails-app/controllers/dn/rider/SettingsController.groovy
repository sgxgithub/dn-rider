package dn.rider

import javax.servlet.http.Cookie

class SettingsController {

    //save the apps for quick access in cookie
    def index(SettingsCommand cmd) {

        String appsQuickAccess = cmd.appsQuickAccess

        if (appsQuickAccess) {
            log.info "Saving appQuickAcces..."
            def cookie = new Cookie('appsQuickAccess', appsQuickAccess )
            cookie.maxAge= 2592000
            cookie.path = '/'
            response.addCookie cookie
        }

        redirect(controller: "home", action: "index")
    }
}