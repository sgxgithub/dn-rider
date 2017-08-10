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

        [app: lastApp, apps: apps as JSON]
    }

    def compare() {
        String app = params.app
        def versionsUrl = params.versions
        String releaseType = params.releaseType ?: 'all'
        String regex = params.regex

        flash.message = ''
        List<String> versionsSelected = makeVersionsFromUrl(app, releaseType, versionsUrl)
        List<JSONObject> dns = []

        versionsSelected.each { String version ->
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

        respond([
                app             : app,
                releaseType     : releaseType,
                regex           : regex,
                versionsSelected: versionsSelected,
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
     */
    def makeVersionsFromUrl(String app, String releaseType, versionsUrl) {
        List<String> versionsSelected = []

        //case 1, 2, 3
        if (versionsUrl instanceof String) {
            versionsSelected.addAll(takeVersionsFromString(app, releaseType, versionsUrl))
        }
        //case 4
        else {
            versionsUrl.each { it ->
                versionsSelected.addAll(takeVersionsFromString(app, releaseType, it.toString()))
            }
        }
        //from oldest to newest
        versionsSelected.unique()

        return versionsSelected
    }

    def takeVersionsFromString(String app, String releaseType, String versionsUrl) {
        List<String> versionsSelected = []
        //case 2
        if (versionsUrl.contains(',')) {
            versionsSelected.addAll(versionsUrl.tokenize(','))
        }
        // case 3
        else if (versionsUrl.contains('>')) {
            def versions = nexusConsumerService.getVersions(app)
            versions = nexusConsumerService.filterVersionsByReleaseType(versions, releaseType)

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
