package dn.rider

import grails.converters.JSON
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import springfox.documentation.annotations.ApiIgnore

@ApiIgnore
class HomeController {

    def nexusConsumerService

    def getAppsQuickAccessArray(){
        def appsQuickAccess = request.cookies.find { it.name == 'appsQuickAccess' }?.value

        if(appsQuickAccess){
            log.info "appsQuickAccess cookie found:" + appsQuickAccess
        }

       return appsQuickAccess?.tokenize('_')
    }

    def index() {
        flash.message = null
        //get the apps for quick access in cookie
        def appsQuickAccessArray = getAppsQuickAccessArray()
        //get the list of apps in service
        def apps = nexusConsumerService.getApps()

        [apps: apps as JSON, appsQuickAccessArray:appsQuickAccessArray]
    }

    def search(SearchCommand cmd) {
        String app = cmd.app
        if (cmd.hasErrors()) {
            //flash.message = cmd.errors.allErrors.toString()
            redirect controller: 'search', action: "index", params: [app: app]
            return
        }
        redirect controller: 'search', action: 'search', params: [app: app]
    }

}
