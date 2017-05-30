package dn.rider.comparison

import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class ComparisonService {

    def compareVersions(version1, version2) {
        if (!version1) return true
        if (version1 == version2) return false
        return true
    }

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
                        rowPackage.put(dn.version, [name: p.version, packageUrl: p.packageUrl])
                        //add tag 'changed'
                        if (i > 0 && compareVersions(rowPackage.(versions[i - 1])?.name, p.version)) {
                            rowPackage[dn.version].put('tag', 'changed')
                        }
                        return true
                    }
                }) { // when the package is new, create a new JSONObject rowPackage
                    JSONObject rowPackage = new JSONObject()
                    rowPackage.put('key', p.key)
                    rowPackage.put(dn.version, [name: p.version, packageUrl: p.packageUrl])
                    //add tag 'new'
                    if (i > 0) {
                        rowPackage.(dn.version).tag = 'new'
                    }
                    rowPackages << rowPackage
                }
            }
        }

        //add 'deleted' tag
        rowPackages.each { rowPackage ->
            versions.eachWithIndex { version, i ->
                if (i > 0) {
                    //if the previous dn has this package
                    if (!rowPackage[version] && rowPackage[versions[i - 1]]) {
                        rowPackage.put(version, [tag: 'deleted'])
                    }
                }
            }
        }
        //sort the row by module and name
        Comparator mc = { a, b ->
            (a.key.module + '/' + a.key.name) <=> (b.key.module + '/' + b.key.name)
        }
        rowPackages.sort(mc)

        return [rowVersions: rowVersions, rowPackages: rowPackages]
    }
}
