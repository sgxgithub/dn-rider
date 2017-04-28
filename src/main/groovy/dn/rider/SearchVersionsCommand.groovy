package dn.rider

class SearchVersionsCommand implements grails.validation.Validateable{
    String app
    String version
    String releaseType

    static constraints = {
        app blank: false, size: 3..6
        version nullable: true
    }
}