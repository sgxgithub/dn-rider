package dn.rider.geb

import geb.spock.GebSpec
import grails.test.mixin.integration.Integration

@SuppressWarnings('MethodName')
@Integration
class HomePageSpec extends GebSpec {

    def 'verifies the three nav buttons when we visit the home page.'() {
        when:
        go '/'

        then:
        assert $("a", href: "/deliveryNotes/search/index").size() == 1
        assert $("a", href: "/deliveryNotes/comparison/index").size() == 1
        assert $("a", href: "/deliveryNotes/edition/index").size() == 1
    }
}
