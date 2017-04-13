package dn.rider.consumer.nexus

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class NexusConsumerService {

    def getDnJson(String app, String version) {
        log.info "searching for the delivery-notes..."
        String url = "http://nexus:50080/nexus/service/local/artifact/maven/content?r=public&g=com.vsct.${app}&a=delivery-notes&v=${version}&p=json"
        RestBuilder rest = new RestBuilder()

        def dn = rest.get(url)
        assert dn.json instanceof JSONObject
/*
        Dn dnObj = new Dn()
        dnObj.packages = []
        dn.json."NDL_pour_rundeck".packages.each { p ->
            dnObj.packages << p
        }


*/
        log.info "sending the delivery-notes to dnController..."
        return dn.json

    }

}
