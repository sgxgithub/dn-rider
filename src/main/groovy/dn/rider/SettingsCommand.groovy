package dn.rider

class SettingsCommand implements grails.validation.Validateable{
    String appsQuickAccess

    static constraints = {
        appsQuickAccess nullable: true
    }
}