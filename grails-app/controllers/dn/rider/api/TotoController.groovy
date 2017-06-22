package dn.rider.api

import grails.converters.JSON
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation

import javax.ws.rs.Path

// TODO supprimer ce controlleur. Là uniquement pour montrer l'utilisation de swagger
@Api(value = "toto")
class TotoController {

    static allowedMethods = [doGet: "GET", doPost: "POST"]

    @Path("/doget/{app}")
    @ApiOperation(value = "do a get")
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'name', paramType = 'query', required = true, dataType = 'string'),
            @ApiImplicitParam(name = 'app', paramType = 'query', required = true, dataType = 'string'),

    ])
    def doGet() {
        render "hey ${params.app} ${params.name}"
    }

    @ApiOperation(value = "do a post")
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'app', paramType = 'form', required = true, dataType = 'string'),
    ])
    def doPost() {
        log.info "${params}"
        def ret = [
                name : params.app
        ]
        render ret as JSON
    }
}
