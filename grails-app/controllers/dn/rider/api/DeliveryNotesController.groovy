package dn.rider.api

import grails.converters.JSON
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

@Api(value = "/api", tags = ["DeliveryNotes"], description = "Dn-Rider APIs")
class DeliveryNotesController {

    def nexusConsumerService
    def jsonSchemaValidationService

    @ApiOperation(
            value = "Récupèrer la liste des applications avec note de livraison",
            nickname = "applications",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET"
    )
    @ApiImplicitParams([
            @ApiImplicitParam(name = "format",
                    paramType = "query",
                    required = false,
                    value = "text/json",
                    dataType = "string")
    ])
    def showApps() {
        String format = params.format ?: 'json'

        def apps = nexusConsumerService.getApps()

        if (format.toUpperCase() == 'TEXT')
            render apps
        else render apps as JSON
    }

    /**
     * TODO: param releaseType cannot be null as path
     * @return
     */
    @ApiOperation(
            value = "Récupèrer la liste des note de livraison",
            nickname = "deliveryNotes/{app}/{releaseType}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET"
    )
    @ApiImplicitParams([
            @ApiImplicitParam(name = "app",
                    paramType = "path",
                    required = true,
                    value = "trigramme",
                    dataType = "string"),
            @ApiImplicitParam(name = "releaseType",
                    paramType = "path",
                    required = true,
                    value = "releases/snapshots/null",
                    dataType = "string"),
            @ApiImplicitParam(name = "format",
                    paramType = "query",
                    required = false,
                    value = "text/json",
                    dataType = "string")
    ])
    def showVersions() {
        String app = params.app
        String releaseType = params.releaseType ?: 'all'
        String format = params.format ?: 'json'

        log.info "searching for the list of delivery-notes with app=${app}, releaseType=${releaseType}..."
        def versions = nexusConsumerService.getVersions(app, releaseType)
        log.info "received the list of delivery-notes"

        if (!versions) {
            render status: 404, text: 'no result found'
            return
        }

        if (format.toUpperCase() == 'TEXT')
            render versions.join(' ')
        else render versions as JSON
    }

    @ApiOperation(
            value = "Récupèrer une note de livraison",
            nickname = "deliveryNotes/{app}/{version}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET"
    )
    @ApiImplicitParams([
            @ApiImplicitParam(name = "app",
                    paramType = "path",
                    required = true,
                    value = "app name",
                    dataType = "string"),
            @ApiImplicitParam(name = "version",
                    paramType = "path",
                    required = true,
                    value = "app version",
                    dataType = "string"),
            @ApiImplicitParam(name = "format",
                    paramType = "query",
                    required = false,
                    value = "result format",
                    dataType = "string")
    ])
    def showDn() {
        String app = params.app
        String version = params.version
        String format = params.format ?: 'json'

        log.info "searching for the delivery-note with app=${app}, version=${version}..."
        def resp = nexusConsumerService.getDn(app, version)
        log.info "received the delivery-note"

        //when there is no result
        if (resp.responseEntity.statusCode.toString() == '404') {
            String dnUrl = getNexusConsumerService().getDnUrl(app, version)
            String message = "No result for app=${app}, version=${version} !\nTried with url: ${dnUrl}"
            render status: 404, text: message
            return
        }

        if (format.toUpperCase() == 'TEXT')
            render resp.text
        else render resp.json
    }

    @ApiOperation(
            value = "Valider une note de livraison stockée",
            nickname = "validations/{app}/{version}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET"
    )
    @ApiResponses([
            @ApiResponse(code = 404,
                    message = "NDL Not Found"),
            @ApiResponse(code = 200,
                    message = "NDL validée"),
            @ApiResponse(code = 422,
                    message = "NDL Non Validé")])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "app",
                    paramType = "path",
                    required = true,
                    value = "app name",
                    dataType = "string"),
            @ApiImplicitParam(name = "version",
                    paramType = "path",
                    required = true,
                    value = "app version",
                    dataType = "string")
    ])
    def validationStored() {
        String app = params.app
        String version = params.version

        log.info "searching for the delivery-note with app=${app}, version=${version}..."
        def resp = nexusConsumerService.getDn(app, version)
        log.info "received the delivery-note"

        //when there is no result
        if (resp.responseEntity.statusCode.toString() == '404') {
            String dnUrl = getNexusConsumerService().getDnUrl(app, version)
            response.status = 404
            String message = "No result for app=${app}, version=${version} !\nTried with url: ${dnUrl}"
            render message
            return
        }

        String dn = resp.text
        String schema = jsonSchemaValidationService.getSchemaText()

        def resValidation = jsonSchemaValidationService.validateSchema(schema, dn)

        setStatus(resValidation)
        render text: resValidation.toString(), contentType: 'application/json'
    }

    @ApiOperation(
            value = "Valider une note de livraison non stockée",
            nickname = "validations",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "POST"
    )
    @ApiResponses([
            @ApiResponse(code = 200,
                    message = "NDL validée"),
            @ApiResponse(code = 422,
                    message = "NDL Non Validé")])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "dn",
                    paramType = "formData",
                    required = true,
                    value = "required dn content",
                    dataType = "string")
    ])
    def validationNoStored() {
        String dn = params.dn ?: ''
        String schema = jsonSchemaValidationService.getSchemaText()

        def res = jsonSchemaValidationService.validateSchema(schema, dn)

        setStatus(res)
        render text: res.toString(), contentType: 'application/json'
    }

    def setStatus(res) {
        if (res['isJsonValid'] && res['isSchemaValid']) response.status = 200
        else response.status = 422
    }

    /**
     * ref: https://support.sonatype.com/hc/en-us/articles/213465818-How-can-I-programmatically-upload-an-artifact-into-Nexus-2-
     * authentification a utiliser pour le moment : jenkins_nexus/Bb&fX!Z9
     * Prevoir un moyen de passer l’authentification dans l’appel REST entrant du DNrider.
     */
    @ApiOperation(
            value = "Stocker une note de livraison",
            nickname = "deliveryNotes/{app}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "POST"
    )
    @ApiResponses([
            @ApiResponse(code = 201,
                    message = "NDL saved"),
            @ApiResponse(code = 404,
                    message = "repo inexistant"),
            @ApiResponse(code = 400,
                    message = "url no valide")])

    @ApiImplicitParams([
            @ApiImplicitParam(name = "dn",
                    paramType = "formData",
                    required = true,
                    value = "required dn content",
                    dataType = "string"),
            @ApiImplicitParam(name = "app",
                    paramType = "path",
                    required = true,
                    value = "app name",
                    dataType = "string"),
            @ApiImplicitParam(name = "version",
                    paramType = "query",
                    required = true,
                    value = "app version",
                    dataType = "string"),
            @ApiImplicitParam(name = "releaseType",
                    paramType = "query",
                    required = false,
                    value = "releases/snapshots",
                    dataType = "string"),
            @ApiImplicitParam(name = "repositoryId",
                    paramType = "query",
                    required = false,
                    value = "repositoryId",
                    dataType = "string"),
    ])
    def saveDn() {
        // get parameters
        def dn = params.dn
        String app = params.app
        String version = params.version
        String releaseType = params.releaseType
        String repositoryId = params.repositoryId

        //validation parameters niveau 1
        if (!version) {
            render status: 400, text: "version n'est pas fourni"
            return
        }
        if (releaseType && (releaseType != 'releases') && (releaseType != 'snapshots')) {
            render status: 400, text: "releaseType pas correct"
            return
        }

        // consolidation parameters
        // identify 'snapshot' in version in uppercase or lowercase, but not transform it
        if (!releaseType) {
            releaseType = version.toUpperCase().contains('SNAPSHOT') ? 'snapshots' : 'releases'
        }

        //check
        if (repositoryId) {
            String repositoryPolicy = nexusConsumerService.getRepositoryPolicy(repositoryId)
            //SI repo inexistant - > return error 404
            if (!repositoryPolicy) {
                render status: 404, text: "repositoryId non validé"
                return
            }
            //SI repo pas conforme au releaseType : return error 400
            if (!releaseType.toLowerCase().contains(repositoryPolicy.toLowerCase())) {
                render status: 400, text: "repositoryId ${repositoryId} n'est pas de release type ${releaseType}"
                return
            }
        } else {
            def repositoryIds = nexusConsumerService.getRepositoryIds(app, releaseType)
            switch (repositoryIds.size()) {
                case 0:
                    render status: 400, text: "non repositoryId trouvé avec l'app=${app} et releaseType=${releaseType}"
                    return
                case 1:
                    break
                default:
                    render status: 400, text: "plusieur repositoryIds trouvé avec l'app=${app} et releaseType=${releaseType}"
                    return
            }
        }

        //si type est RELEASE & donnée déjà présente -> erreur
        if (releaseType == 'releases') {
            def versions = nexusConsumerService.getVersions(app, 'releases')
            if (versions.find { it ->
                it == version
            }) {
                render status: 403, text: 'la version de release déjà présente'
                return
            }
        }

        //save
        def resp = nexusConsumerService.saveDn(dn, app, version, repositoryId)

        if (resp.status == 404) { //nexus ne repond pas : 503
            render status: 503, text: 'Nexus ne respond pas'
        } else if (resp.status == 400) {
            //return 405 when the target is a Maven SNAPSHOT repository
            //when the version contain 'SNAPSHOT', it will be put in the snapshots repo automatically
            render status: 405, text: 'This is a Maven SNAPSHOT repository, and manual upload against it is forbidden!'
        } else {
            render status: 201, json: resp.json
        }
    }

    /**
     * ref: https://stackoverflow.com/questions/34115434/how-to-delete-artifacts-with-classifier-from-nexus-using-rest-api
     * ce qu'il faut supprimer comme metadata ?
     */
    @ApiOperation(
            value = "Supprimer une note de livraison",
            nickname = "deliveryNotes/{app}/{version}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "DELETE"
    )
    @ApiImplicitParams([
            @ApiImplicitParam(name = "app",
                    paramType = "path",
                    required = true,
                    value = "app name",
                    dataType = "string"),
            @ApiImplicitParam(name = "version",
                    paramType = "path",
                    required = true,
                    value = "app version",
                    dataType = "string")
    ])
    def deleteDn() { //TODO: deal with case when there are multiples repos(try with the first ; classer by hosted/proxy)
        String app = params.app
        String version = params.version

        def resp = nexusConsumerService.deleteDn(app, version)

        if (resp.status == 204) {
            render status: 200, text: 'Dn Deleted'
        } else if (resp.status == 404) {
            render status: 404, text: 'Dn Not Found'
        } else render status: 400, text: 'Failed'
    }

    def getPackages() {
        String app = params.app
        String version = params.version
        String format = params.format ?: 'json'

        log.info "searching for the delivery-note with app=${app}, version=${version}..."
        def resp = nexusConsumerService.getDn(app, version)
        log.info "received the delivery-note"

        //when there is no result
        if (resp.responseEntity.statusCode.toString() == '404') {
            String dnUrl = getNexusConsumerService().getDnUrl(app, version)
            String message = "No result for app=${app}, version=${version} !\nTried with url: ${dnUrl}"
            render status: 404, text: message
        } else {
            def packages = getNexusConsumerService().getPackages(app, version)

            if (format.toUpperCase() == 'TEXT')
                render packages
            else render packages as JSON
        }
    }

    def getPackage() {
        String app = params.app
        String version = params.version
        String id = params.id
        String format = params.format ?: 'json'

        log.info "searching for the delivery-note with app=${app}, version=${version}..."
        def resp = nexusConsumerService.getDn(app, version)
        log.info "received the delivery-note"

        //when there is no result
        if (resp.responseEntity.statusCode.toString() == '404') {
            String dnUrl = getNexusConsumerService().getDnUrl(app, version)
            String message = "No result for app=${app}, version=${version} !\nTried with url: ${dnUrl}"
            render status: 404, text: message
        } else {
            def packages = resp.json.NDL_pour_rundeck.packages
            def pack

            boolean isExist = packages.any { p ->
                if ((p.name + '@' + p.module) == id) {
                    pack = p
                    return true
                }
            }

            if (isExist) {
                if (format.toUpperCase() == 'TEXT')
                    render pack
                else render pack as JSON
            } else {
                String message = "No package found with id=${id}!"
                render status: 404, text: message
            }
        }
    }
}