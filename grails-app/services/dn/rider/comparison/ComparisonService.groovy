package dn.rider.comparison

import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class ComparisonService {

    def sortPackages(app, dns) {
        //sort the dns by date
//        dns.sort { a, b ->
//            a.date <=> b.date
//        }

        List<String> versions = dns.version
        //the table head
        List<JSONObject> rowVersions = []

        dns.each { dn ->
            JSONObject rowVersion = new JSONObject()
            rowVersion.put('name', dn.version)
            rowVersion.put('date', dn.date)
            rowVersion.put('url', "/deliveryNotes/search/search?app=${app}&version=${dn.version}")
            rowVersions << rowVersion
        }

        //the table body
        List<JSONObject> rowPackages = []
        dns.eachWithIndex { dn, i ->
            def packages = dn.packages
            packages.each { p ->
                JSONObject key = [module: p.module, name: p.name.toString() - ('-' + p.version.toString())]
                //if the package exist, add the version to the JSONObject rowPackage
                if (!rowPackages.any() { rowPackage ->
                    if (rowPackage.key == key) {
                        JSONObject element = makeElement(p)
                        rowPackage.put(dn.version, element)
                        return true
                    }
                }) { // when the package is new, create a new JSONObject rowPackage
                    JSONObject rowPackage = new JSONObject()
                    rowPackage << [key: key]
                    JSONObject element = makeElement(p)
                    rowPackage.put(dn.version, element)
                    rowPackages << rowPackage
                }
            }
        }

        rowPackages = addTags(rowPackages, versions)

        //sort the row by module and name
        Comparator mc = { a, b ->
            (a.key.module + '/' + a.key.name) <=> (b.key.module + '/' + b.key.name)
        }
        rowPackages.sort(mc)

        return [rowVersions: rowVersions, rowPackages: rowPackages]
    }

    def makeElement(p) {
        JSONObject element = new JSONObject()
        element << [content: p]

        //add url and name
        if (p.packageUrl) { //package url exists
            element << [url: p.packageUrl]
            if (p.version) {
                element << [name: p.version]
            } else {
                element << [name: 'package']
            }
            //hesperides url exists
            //example for hesperidesModule: ccl/73.00.0-0 espaceclient-vhost
            //example for hesperidesApplication: sum/1.60.0
        } else if (p.hesperidesModule || p.hesperidesApplication) {
            def (name, hesperidesUrl) = makeHesperidesUrl(p)
            element << [name: name]
            element << [url: hesperidesUrl]
        } else { //no url
            if (p.version) { //example : wdi wdiSgbdAdminStat-oraclePkg
                element << [name: p.version]
            } else if (p.filename) { // example: wdi propertiesLink
                element << [name: "file"]
            } else element << [name: "N/A"]
        }

        return element
    }

    def makeHesperidesUrl(p) {
        def module = p.hesperidesModule ?: p.hesperidesApplication
        def version = p.hesperidesVersion ?: p.version
        def name = "${module}/${version}"
        def hesperidesUrl = "https://hesperides:50101/#/module/${module}/${version}"

        return [name, hesperidesUrl]
    }

    def addTags(List<JSONObject> rowPackages, versions) {
        //add  tag 'deleted', 'new', 'changed'
        rowPackages.each { rowPackage ->
            versions.eachWithIndex { version, i ->
                if (i > 0) {
                    //if the previous dn has this package
                    if (!rowPackage[version]?.name && rowPackage[versions[i - 1]]?.name) {
                        rowPackage.put(version, [tag: 'deleted'])
                    }
                    //if the previous dn does not have this package
                    else if (rowPackage[version]?.name && !rowPackage[versions[i - 1]]?.name) {
                        rowPackage[version].put('tag', 'new')
                    }
                    //add tag 'changed'
                    else if (compareVersions(rowPackage[version]?.name, rowPackage[versions[i - 1]]?.name)) {
                        if (rowPackage[version]) {
                            rowPackage[version].put('tag', 'changed')
                        } else {
                            rowPackage.put(version, [tag: 'changed'])
                        }

                    }
                }
            }
        }
        return rowPackages
    }

    def compareVersions(version1, version2) {
        if (!version1 || !version2) return false
        if (version1 == version2) return false
        return true
    }
}
