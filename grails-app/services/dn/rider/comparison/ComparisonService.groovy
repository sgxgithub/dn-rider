package dn.rider.comparison

import com.google.gson.JsonArray
import grails.transaction.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Transactional
class ComparisonService {

    def compareVersions(version1, version2) {
        if (!version1) return true
        if (version1 == version2) return false
        return true
    }

    def sortPackages(dns) {
        List<JsonArray> listPackages = []
        List<String> packageIds = []

        dns.eachWithIndex { dn, i ->
            //add id attribute to package
            def packages = dn.packages
            packages.each { p ->
                p.id = p.name.toString() - ('-' + p.version.toString()) + '/' + p.module
            }

            //initialise with the earliest version
            if (i == 0) {
                packages.each {
                    packageIds << it.id
                }
                listPackages << packages
            } else {
                JSONArray packagesOrderd = []
                packageIds.eachWithIndex { packageId, j ->
                    boolean isExist = packages.any { p ->
                        if (p.id == packageId) {
                            if (compareVersions(listPackages[i - 1][j]?.version, p.version)) {
                                p.tag = 'updated'
                            }
                            packagesOrderd.add(p)
                            packages.remove(p)
                            return true
                        }
                    }
                    if (!isExist) {
                        JSONObject p = new JSONObject()
                        p.put('tag', 'deleted')
                        packagesOrderd << p
                    }
                }
                if (packages) {
                    packageIds << packages.id
                    packages.each { p ->
                        p.tag = 'new'
                        packagesOrderd << p
                    }
                }
                listPackages << packagesOrderd
            }
        }

        return [packageIds, listPackages]
    }
}
