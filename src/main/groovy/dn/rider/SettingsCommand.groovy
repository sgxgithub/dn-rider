package dn.rider

class SettingsCommand implements grails.validation.Validateable{
    String app

    static constraints = {
        app nullable: true
    }
}