package dn.rider

class SearchDnCommand implements grails.validation.Validateable {
    String app
    String version
    String formatShow

    static constraints = {
        app blank: false, size: 3..15
        version blank: false
    }
}