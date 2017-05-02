package dn.rider

import dn.rider.content.DnPackage
import grails.converters.JSON
import org.grails.web.json.JSONObject

class DnController {

    def nexusConsumerService

    def index() {}

    def show() {
        String app = params.app
        String version = params.version

        log.info "searching for the delivery-notes with app=${app}, version=${version}..."

        def json = nexusConsumerService.getDnJson(app,version)

        log.info "received the delivery-notes"


     /*   //dnPackage
        JSONObject j = json.NDL_pour_rundeck.packages[0]
        //render dn as JSON
        def DnPackage = new DnPackage(jPackage)

        render dnPackage as JSON
*/

/*        //dn
        JSONObject jDn = json.NDL_pour_rundeck
        def oDn = new Dn(jDn)
      */
       // render oDn as JSON
        respond([size_packages: json.NDL_pour_rundeck.packages.size(), packages: json.NDL_pour_rundeck.packages, app:app, version:version])
    }
}