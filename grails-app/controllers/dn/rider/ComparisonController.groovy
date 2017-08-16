package dn.rider

import grails.converters.JSON
import org.grails.web.json.JSONObject
import java.text.SimpleDateFormat

class ComparisonController {

    def nexusConsumerService
    def comparisonService
    def cookiesService

    def index() {
        flash.message = null

        def apps = nexusConsumerService.getApps()
        String lastApp = cookiesService.getLastApp()
        def versions = []
        if (lastApp) {
            versions = nexusConsumerService.getVersions(lastApp)
        }

        [app: lastApp, apps: apps as JSON, versions: versions]
    }

    def compare() {
        String app = params.app
        def versionsUrl = params.versions
        String releaseType = params.releaseType ?: 'all'
        String regex = params.regex ?: ''

        flash.error = ''
        def versions = nexusConsumerService.getVersions(app)
        versions = nexusConsumerService.filterVersions(versions, releaseType, regex)
        List<String> versionsSelected = makeVersionsFromUrl(versions, versionsUrl)
        List<JSONObject> dns = []

        versionsSelected.each { String version ->
            log.info "searching for the delivery-note with app=${app}, version=${version}..."
            def resp = nexusConsumerService.getDn(app, version)

            //when there is no result for this version
            //example : app = ner, version = 1.36.1.0-SNAPSHOT
            if (resp.responseEntity.statusCode.toString() == '404') {
                String dnUrl = getNexusConsumerService().getDnUrl(app, version)
                String msg = message(code: "dn.rider.comparison.msg.noResultFound", args: [app, version, dnUrl])
                log.info msg
                flash.error += "${msg}\n"
            } else {
                log.info "received the delivery-note"
                JSONObject dn = resp.json?.NDL_pour_rundeck
                if (!dn) {
                    String msg = message(code: "dn.rider.comparison.msg.noDnFound", args: [app, version])
                    flash.error += "${msg}\n"
                } else {
                    //create a new JSONObject to avoid operating the variable 'resp', otherwise the cache of dn will be changed
                    JSONObject json = new JSONObject()
                    json << dn

                    def sdf = new SimpleDateFormat('EEE, d MMM yyyy HH:mm:ss z', Locale.ENGLISH)
                    def date = sdf.parse(resp.headers['Last-Modified'][0])
                    json.put('version', version)
                    json.put('date', date)
                    dns << json
                }
            }
        }

        def table = comparisonService.sortPackages(app, dns)

        respond([
                app             : app,
                releaseType     : releaseType,
                regex           : regex,
                versions        : versions,
                versionsSelected: versionsSelected as JSON,
                rowVersions     : table.rowVersions,
                rowPackages     : table.rowPackages
        ], view: 'index')
    }

    /**
     * possible versions :
     * case 1 : versions=v1
     * case 2 : versions=v1,v2,v3
     * case 3 : versions=v1>v3
     * case 4 ( un mélange des 3 cas): versions=v1>v3&versions=v4>v7&versions=v9,v10&versions=v12
     * releaseType & regex est valable sur une portée
     */
    def makeVersionsFromUrl(versions, versionsUrl) {
        List<String> versionsSelected = []

        //case 1, 2, 3
        if (versionsUrl instanceof String) {
            versionsSelected.addAll(takeVersionsFromString(versions, versionsUrl))
        }
        //case 4
        else {
            versionsUrl.each { it ->
                versionsSelected.addAll(takeVersionsFromString(versions, it.toString()))
            }
        }
        //from oldest to newest
        versionsSelected.unique()

        return versionsSelected
    }

    def takeVersionsFromString(versions, String versionsUrl) {
        List<String> versionsSelected = []
        //case 2
        if (versionsUrl.contains(',')) {
            versionsSelected.addAll(versionsUrl.tokenize(','))
        }
        // case 3
        else if (versionsUrl.contains('>')) {


            def versionBegin = versionsUrl.tokenize('>')[0]
            def versionEnd = versionsUrl.tokenize('>')[1]

            int indexBegin = versions.findIndexOf { version -> version == versionBegin }
            int indexEnd = versions.findIndexOf { version -> version == versionEnd }

            if (indexBegin == -1) {
                flash.message += "The version : ${versionBegin} doesn't exist ! \n"
            } else if (indexEnd == -1) {
                flash.message += "The version : ${versionEnd} doesn't exist ! \n"
            } else {
                if (indexBegin > indexEnd) {
                    int tmp = indexBegin
                    indexBegin = indexEnd
                    indexEnd = tmp
                }
                versionsSelected.addAll(versions.subList(indexBegin, indexEnd + 1))
            }
        }
        //case 1
        else versionsSelected.add(versionsUrl)

        return versionsSelected
    }
}
