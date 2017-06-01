package dn.rider.comparison

import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class ComparisonService {

    def sortPackages(app, dns, versions) {
        //the table head
        List<JSONObject> rowVersions = []
        versions.each { version ->
            JSONObject rowVersion = new JSONObject()
            rowVersion.put('name', version)
            rowVersion.put('url', "/deliveryNotes/search/search?app=${app}&version=${version}")
            rowVersions << rowVersion
        }

        //the table body
        List<JSONObject> rowPackages = []
        dns.eachWithIndex { dn, i ->
            def packages = dn.packages
            packages.each { p ->
                p.key = [module: p.module, name: p.name.toString() - ('-' + p.version.toString())]
                //if the package exist, add the version to the JSONObject rowPackage
                if (!rowPackages.any() { rowPackage ->
                    if (rowPackage.key == p.key) {
                        if (p.type == 'propertiesLink') {
                            rowPackage.put(dn.version, [name: p.name])
                        } else {
                            rowPackage.put(dn.version, [name: p.version, packageUrl: p.packageUrl])
                        }
//                        rowPackage.put(dn.version, [name: p.version, packageUrl: p.packageUrl])
                        return true
                    }
                }) { // when the package is new, create a new JSONObject rowPackage
                    JSONObject rowPackage = new JSONObject()
                    rowPackage.put('key', p.key)
                    if (p.type == 'propertiesLink') {
                        rowPackage.put(dn.version, [name: p.name])
                    } else {
                        rowPackage.put(dn.version, [name: p.version, packageUrl: p.packageUrl])
                    }
//                    rowPackage.put(dn.version, [name: p.version, packageUrl: p.packageUrl])
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
