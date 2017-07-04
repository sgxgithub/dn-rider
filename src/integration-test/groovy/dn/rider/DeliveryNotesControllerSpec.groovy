package dn.rider

import grails.core.GrailsApplication
import grails.plugins.rest.client.RestBuilder
import grails.test.mixin.integration.Integration
import grails.transaction.*
import grails.web.mapping.LinkGenerator
import org.springframework.beans.factory.annotation.Value
import spock.lang.*
import geb.spock.*

/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */
@Integration
@Rollback
class DeliveryNotesControllerSpec extends Specification {

    @Value('${local.server.port}')
    Integer serverPort

    def setup() {
    }

    def cleanup() {
    }

    void "testShowApp"() {
        given:
            RestBuilder rest = new RestBuilder()

        when: "we ask for applications list without format information"
            def resp = rest.get("http://localhost:${serverPort}/api/applications")

        then: "we have a list of apps in json format"
            assert resp.status == 200
            assert resp.headers["Content-Type"].any { it.contains("application/json") }
            assert resp.json
            assert resp.json.size() > 50

        when: "we ask for applications list with format information set to text"
            resp = rest.get("http://localhost:${serverPort}/api/applications?format=text")

        then: "we have a list of apps in text format"
            assert resp.status == 200
            assert resp.headers["Content-Type"].any { it.contains("text/html") }
            assert resp.text
            assert resp.text.length() > 100

        when: "we ask for applications list with format information set to json"
            resp = rest.get("http://localhost:${serverPort}/api/applications?format=json")

        then: "we have a list of apps in text format"
            assert resp.status == 200
            assert resp.headers["Content-Type"].any { it.contains("application/json") }
            assert resp.json
            assert resp.json.size() > 50

    }
}
