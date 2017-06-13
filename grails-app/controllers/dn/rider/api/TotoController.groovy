package dn.rider.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation

@Api(value="toto")
class TotoController {

    static allowedMethods = [doGet: "GET", doPost: "POST"]

    @ApiOperation(value = "do a get")
    def doGet() {

    }

    @ApiOperation(value = "do a post")
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'body', paramType = 'body', required = true, dataType = 'Demo'),
    ])
    def doPost() {

    }
}
