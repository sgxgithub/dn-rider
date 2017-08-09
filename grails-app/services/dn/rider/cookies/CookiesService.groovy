package dn.rider.cookies

import grails.transaction.Transactional
import org.grails.web.util.WebUtils

import javax.servlet.http.Cookie

@Transactional
class CookiesService {

    //get the apps for quick access from cookies
    def getAppsQuickAccessArray() {
        def request = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
        def appsQuickAccess = request.cookies.find { it.name == 'appsQuickAccess' }?.value

        if (appsQuickAccess) {
            log.info "appsQuickAccess cookie found:${appsQuickAccess}"
        }

        return appsQuickAccess?.tokenize('_')
    }

    def saveAppsQuickAccessArray(String appsQuickAccess) {
        def response = WebUtils.retrieveGrailsWebRequest().getCurrentResponse()
        //save the cookie even if it is null
        log.info "Saving appQuickAcces..."
        def cookie = new Cookie('appsQuickAccess', appsQuickAccess)
        cookie.maxAge = 2592000
        cookie.path = '/'
        response.addCookie cookie
    }

    def getLastApp() {
        def request = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()

        String lastApp = request.cookies.find { it.name == 'lastApp' }?.value

        if (lastApp) {
            log.info "lastApp cookie found:${lastApp}"
        }

        return lastApp
    }

    def saveLastApp(String app) {
        def response = WebUtils.retrieveGrailsWebRequest().getCurrentResponse()

        log.info "Saving lastApp in cookie..."
        def cookie = new Cookie('lastApp', app)
        cookie.maxAge = 2592000
        cookie.path = '/'
        response.addCookie cookie
    }
}
