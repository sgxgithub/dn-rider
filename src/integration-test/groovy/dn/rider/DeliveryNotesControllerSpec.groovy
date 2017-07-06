package dn.rider

import grails.plugins.rest.client.RestBuilder
import grails.test.mixin.integration.Integration
import grails.transaction.*
import org.springframework.beans.factory.annotation.Value
import spock.lang.*

@Integration
@Rollback
class DeliveryNotesControllerSpec extends Specification {

    @Value('${local.server.port}')
    Integer serverPort

    /**
     * test for show apps
     */

    void "testShowAppsNoParam"() {
        given:
        RestBuilder rest = new RestBuilder()

        when: "we ask for applications list without format information"
        def resp = rest.get("http://localhost:${serverPort}/api/applications")

        then: "we have a list of apps in json format"
        assert resp.status == 200
        assert resp.headers["Content-Type"].any { it.contains("application/json") }
        assert resp.json
        assert resp.json.size() > 50
    }

    void "testShowAppsTextFormat"() {

        given:
        RestBuilder rest = new RestBuilder()

        when: "we ask for applications list with format information set to text"
        def resp = rest.get("http://localhost:${serverPort}/api/applications?format=text")

        then: "we have a list of apps in text format"
        assert resp.status == 200
        assert resp.headers["Content-Type"].any { it.contains("text/html") }
        assert resp.text
        assert resp.text.length() > 100
    }

    void "testShowAppsJSONFormat"() {

        given:
        RestBuilder rest = new RestBuilder()

        when: "we ask for applications list with format information set to json"
        def resp = rest.get("http://localhost:${serverPort}/api/applications?format=json")

        then: "we have a list of apps in text format"
        assert resp.status == 200
        assert resp.headers["Content-Type"].any { it.contains("application/json") }
        assert resp.json
        assert resp.json.size() > 50
    }

    /**
     * test for show versions
     */

    void "testShowVersionsNoParam"() {
        given:
        RestBuilder rest = new RestBuilder()
        String app = 'ccl'

        when: "we ask for applications list without releaseType information"
        def resp = rest.get("http://localhost:${serverPort}/api/deliveryNotes/${app}")

        then: "we have a list of versions in json format"
        assert resp.status == 200
        assert resp.headers["Content-Type"].any { it.contains("application/json") }
        assert resp.json
        assert resp.json.size() > 20
    }

    void "testShowVersionsJSONFormat"() {
        given:
        RestBuilder rest = new RestBuilder()
        String app = 'ccl'

        when: "we ask for applications list with format information set to json"
        def resp = rest.get("http://localhost:${serverPort}/api/deliveryNotes/${app}?format=json")

        then: "we have a list of versions in json format"
        assert resp.status == 200
        assert resp.headers["Content-Type"].any { it.contains("application/json") }
        assert resp.json
        assert resp.json.size() > 20
    }

    void "testShowVersionsTextFormat"() {
        given:
        RestBuilder rest = new RestBuilder()
        String app = 'ccl'

        when: "we ask for applications list witho format information set to text"
        def resp = rest.get("http://localhost:${serverPort}/api/deliveryNotes/${app}?format=text")

        then: "we have a list of versions in text format"
        assert resp.status == 200
        assert resp.headers["Content-Type"].any { it.contains("text/html") }
        assert resp.text
        assert resp.text.length() > 5
    }

    void "testShowVersionsReleases"() {
        given:
        RestBuilder rest = new RestBuilder()
        String app = 'ccl'

        when: "we ask for applications list witho releaseType set to releases"
        def resp = rest.get("http://localhost:${serverPort}/api/deliveryNotes/${app}/releases")

        then: "we have a list of versions of releases in json format"
        assert resp.status == 200
        assert resp.headers["Content-Type"].any { it.contains("application/json") }
        assert resp.json

        def versions = resp.json
        versions.each { version ->
            assert !versions.toString().toUpperCase().contains('SNAPSHOT')
        }
    }

    void "testShowVersionsSnapthots"() {
        given:
        RestBuilder rest = new RestBuilder()
        String app = 'ccl'

        when: "we ask for applications list witho releaseType set to snapshots"
        def resp = rest.get("http://localhost:${serverPort}/api/deliveryNotes/${app}/snapshots")

        then: "we have a list of versions of snapshots in json format"
        assert resp.status == 200
        assert resp.headers["Content-Type"].any { it.contains("application/json") }
        assert resp.json

        def versions = resp.json
        versions.each { version ->
            assert versions.toString().toUpperCase().contains('SNAPSHOT')
        }
    }

    void "testShowVersionsNoResult"() {
        given:
        RestBuilder rest = new RestBuilder()
        String app = 'notAName'

        when: "we ask for applications list with an app that doesn't exist"
        def resp = rest.get("http://localhost:${serverPort}/api/deliveryNotes/${app}")

        then: "we have a 404"
        assert resp.status == 404
    }

    /**
     * test for show dn
     */
    void "testShowDnNoParam"() {
        given:
        RestBuilder rest = new RestBuilder()
        String app = 'ccl'
        String version = '74.00.0-0'

        when: "we ask for a delivery-notes"
        def resp = rest.get("http://localhost:${serverPort}/api/deliveryNotes/${app}/${version}")

        then: "we have a delivery-notes in json format"
        assert resp.status == 200
        assert resp.headers["Content-Type"].any { it.contains("application/json") }
        assert resp.json
    }

    void "testShowDnJSONFormat"() {
        given:
        RestBuilder rest = new RestBuilder()
        String app = 'ccl'
        String version = '74.00.0-0'

        when: "we ask for a delivery-notes"
        def resp = rest.get("http://localhost:${serverPort}/api/deliveryNotes/${app}/${version}?format=json")

        then: "we have a delivery-notes in json format"
        assert resp.status == 200
        assert resp.headers["Content-Type"].any { it.contains("application/json") }
        assert resp.json
    }

    void "testShowDnTextFormat"() {
        given:
        RestBuilder rest = new RestBuilder()
        String app = 'ccl'
        String version = '74.00.0-0'

        when: "we ask for a delivery-notes"
        def resp = rest.get("http://localhost:${serverPort}/api/deliveryNotes/${app}/${version}?format=text")

        then: "we have a delivery-notes in json format"
        assert resp.status == 200
        assert resp.headers["Content-Type"].any { it.contains("text/html") }
        assert resp.text
    }

    void "testShowDnNoResult"() {
        given:
        RestBuilder rest = new RestBuilder()
        String app = 'notAName'
        String version = '74.00.0-0'

        when: "we ask for a delivery-notes that doesnot exist"
        def resp = rest.get("http://localhost:${serverPort}/api/deliveryNotes/${app}/${version}")

        then: "we have a 404"
        assert resp.status == 404
    }

}
