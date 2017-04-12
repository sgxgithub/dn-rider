package dn.rider.consumer.nexus

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional

@Transactional
class NexusConsumerService {

    def getDnJson(String app,String version) {

        //String url = "http://nexus/service/local/repositories/${app}/content/com/vsct/kli/delivery-notes/${version}/delivery-notes-${version}.json"
        String url = "http://nexus:50080/nexus/service/local/artifact/maven/content?r=public&g=com.vsct./${app}&a=delivery-notes&v=${version}&p=json"

        RestBuilder rest = new RestBuilder()
        def dn = rest.get(url)
        //dn.json instanceof JSONObject

        //def dnObject = new Dn(JSON.parse(dn.json))
        return  dn.json;
        //return dnObject;
    }

    def getDnJsonList(String app) {

        String url = "http://nexus/service/local/repositories/${app}/content/com/vsct/kli/delivery-notes/${version}/delivery-notes-${version}.json"
        //String url = "http://nexus:50080/nexus/service/local/artifact/maven/content?r=public&g=com.vsct.ccl&a=delivery-notes&v=52.00.0-2&p=json"

        RestBuilder rest = new RestBuilder()
        def dn = rest.get(url)
        //dn.json instanceof JSONObject

        //def dnObject = new Dn(JSON.parse(dn.json))
        return  dn.json;
        //return dnObject;
    }
}
