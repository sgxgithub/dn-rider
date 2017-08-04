package dn.rider

import grails.converters.JSON

class EditionController {

    def nexusConsumerService
    def jsonSchemaValidationService

    def index() {
        def apps = nexusConsumerService.getApps()
        [apps: apps as JSON]
    }

    def saveDn() {
        def dn = params.dn
        String app = params.app
        String repo = params.repo
        String version = params.version

        def resp = nexusConsumerService.saveDn(dn, app, version, repo)

        //return 405 when the target is a Maven SNAPSHOT repository
        //when the version contain 'SNAPSHOT', it will be put in the snapshots repo automatically
        if (resp.status == 400) {
            flash.error = 'This is a Maven SNAPSHOT repository, and manual upload against it is forbidden!'
            render view: 'index'
        } else {
            flash.success = "Dn Saved !" + resp.json
            render view: 'index'
        }
    }

    def validateSchema(ValidateSchemaCommand cmd) {
        String schema = jsonSchemaValidationService.getSchemaText()
        String dn = cmd.dn

        def validationResult = null

        if (schema && dn) {
            validationResult = jsonSchemaValidationService.validateSchema(schema, dn)
        }

        respond([validationResult: validationResult, dn: dn], view: 'index')
    }

    def editDnFromNexus() {
        String app = params.app
        String version = params.version

        log.info "searching for the delivery-note with app=${app}, version=${version}..."
        def resp = nexusConsumerService.getDn(app, version)
        log.info "received the delivery-note"

        String dn = resp.text

        respond([dn: dn], view: 'index')
    }

    //for ajax usage
    def getRepos(){
        String app = params.app

        def repos = nexusConsumerService.getRepoIdsAndNames(app)

        render repos as JSON
    }
}
