package dn.rider

class SearchVersionsCommand {
    String app
    String version
    String ReleaseType

    static constraints = {
        app blank:false, size:3..6
        version nullable:true
    }
}