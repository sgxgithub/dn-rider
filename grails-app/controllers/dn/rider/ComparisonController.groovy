package dn.rider

import grails.converters.JSON
import org.grails.web.json.JSONObject

import java.text.SimpleDateFormat

class ComparisonController {

    def nexusConsumerService
    def comparisonService

    def index() {
        flash.message = null

        def apps = nexusConsumerService.getApps()
        [apps: apps as JSON]
    }

    def compare() {
        String app = params.app
        List<String> versions = []
        if (params.versions) {
            versions.addAll(params.versions)
        }
        //from oldest to newest
        versions.reverse(true)

        if (!app || !versions) {
            render ''
            return
        }

        List<JSONObject> dns = []
        flash.message = ''

        versions.each { String version ->
            log.info "searching for the delivery-note with app=${app}, version=${version}..."
            def resp = nexusConsumerService.getDn(app, version)

            //when there is no result for this version
            //example : app = ner, version = 1.36.1.0-SNAPSHOT
            if (resp.responseEntity.statusCode.toString() == '404') {
                String dnUrl = getNexusConsumerService().getDnUrl(app, version)
                log.info "No result for app=${app}, version=${version} ! Tried with url: \n${dnUrl}"
                flash.message += "No result for app=${app}, version=${version} ! Tried with url: \n${dnUrl} \n"
            } else {
                log.info "received the delivery-note"
                def dn = resp.json.NDL_pour_rundeck
                def sdf = new SimpleDateFormat('EEE, d MMM yyyy HH:mm:ss z', Locale.ENGLISH)
                def date = sdf.parse(resp.headers['Last-Modified'][0])
                dn.put('version', version)
                dn.put('date', date)
                dns << dn
            }
        }

        def table = comparisonService.sortPackages(app, dns)

        render template: "content", model: [app: app, rowVersions: table.rowVersions, rowPackages: table.rowPackages]
    }
}
