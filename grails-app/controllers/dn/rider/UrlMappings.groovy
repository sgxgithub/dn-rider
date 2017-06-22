package dn.rider

class UrlMappings {

    static mappings = {

        "/deliveryNotes/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/api/$controller/$action"()

        "/$controller/$action?"()

        "/deliveryNotes/search"(controller: "searchDn")

        "/toto/dopost"(controller: 'toto', action: 'doPost')
        "/toto/doget/$name?"(controller: 'toto', action: 'doGet')

        "/"(controller: 'home')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}