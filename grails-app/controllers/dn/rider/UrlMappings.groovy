package dn.rider

class UrlMappings {

    static mappings = {

        "/deliveryNotes/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        get "/api/deliveryNotes/app/${app}/version/${version}"(controller:"deliveryNotes", action:"show")

        "/deliveryNotes/search"(controller:"searchDn")

        "/"(controller: 'home')
        "500"(view: '/error')
        "404"(view: '/notFound')

    }
}