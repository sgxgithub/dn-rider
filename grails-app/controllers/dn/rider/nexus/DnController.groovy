package dn.rider.nexus

import grails.converters.JSON

class DnController {

    def nexusConsumerService

    def index() {}

    def show() {
        // render "hello ! la fonction show !"
        //Dn dn = nexusConsumerService.getDnConstante()
        //render dn as JSON

        render nexusConsumerService.getDnConstante()

        /*
         ("""
         {"name":"toto" }
 """) as JSON
     */
    }
}
