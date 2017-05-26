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

//    def sortPackages(dns) {
//        List<JsonArray> listPackages = []
//        List<JSONObject> packageKeys = []
//
//        dns.eachWithIndex { dn, i ->
//            //add key attribute to package
//            def packages = dn.packages
//            packages.each { p ->
//                p.key = [module: p.module, name: p.name.toString() - ('-' + p.version.toString())]
//            }
//
//            //initialise with the earliest version
//            if (i == 0) {
//                packages.each {
//                    packageKeys << it.key
//                }
//                listPackages << packages
//            } else {
//                JSONArray packagesOrderd = []
//                packageKeys.eachWithIndex { packageKey, j ->
//                    boolean isExist = packages.any { p ->
//                        if (p.key == packageKey) {
//                            if (compareVersions(listPackages[i - 1][j]?.version, p.version)) {
//                                p.tag = 'changed'
//                            }
//                            packagesOrderd.add(p)
//                            packages.remove(p)
//                            return true
//                        }
//                    }
//                    if (!isExist) {
//                        JSONObject p = new JSONObject()
//                        p.put('tag', 'deleted')
//                        packagesOrderd << p
//                    }
//                }
//                if (packages) {
//                    packageKeys.addAll(packages.key)
//                    packages.each { p ->
//                        p.tag = 'new'
//                        packagesOrderd << p
//                    }
//                }
//                listPackages << packagesOrderd
//            }
//        }
//
//        return [packageKeys, listPackages]
//    }

    def sortPackages(dns, versions) {
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

        return rowPackages
    }
}
