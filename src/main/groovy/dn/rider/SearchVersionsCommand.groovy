package dn.rider

class SearchVersionsCommand implements grails.validation.Validateable{
    String app
    String version
    String releaseType
    String formatShow

    static constraints = {
        app blank: false, size: 3..6
        version nullable: true
    }
}