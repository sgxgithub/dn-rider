package dn.rider

class SearchCommand implements grails.validation.Validateable {
    String app
    String version
    String releaseType

    static constraints = {
        app blank: false, size: 3..15
        version nullable: true
        releaseType nullable: true
    }
}