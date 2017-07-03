package dn.rider.api

import grails.converters.JSON
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

@Api(value = "/api", tags = ["Toto"], description = "Toto Api's")
class TotoController {
    @ApiOperation(
            value = "get app name",
            nickname = "toto/doget/{app}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET"
    )
    @ApiResponses([
            @ApiResponse(code = 405,
                    message = "Method Not Allowed. Only GET is allowed"),

            @ApiResponse(code = 404,
                    message = "Method Not Found")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "app",
                    paramType = "path",
                    required = true,
                    value = "app name",
                    dataType = "string"),
            @ApiImplicitParam(name = "version",
                    paramType = "query", required = true,
                    value = "app version", dataType = "string")
    ])
    def doGet() {
        render "hey ${params.app} de version ${params.version}"
    }

    @ApiOperation(
            value = "post dn",
            nickname = "toto/dopost",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "POST"
    )
    @ApiImplicitParams([
            @ApiImplicitParam(name = "dn",
                    paramType = "formData",
                    required = true,
                    value = "required dn content",
                    dataType = "string")
    ])
    def doPost() {
        log.info "${params}"
        def ret = [
                name : params.dn
        ]
        render ret as JSON
    }
}
