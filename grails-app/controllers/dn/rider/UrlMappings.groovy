package dn.rider

class UrlMappings {

    static mappings = {

        "/deliveryNotes/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/deliveryNotes/search"(controller:"searchDn")

        "/"(view: '/home/index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}