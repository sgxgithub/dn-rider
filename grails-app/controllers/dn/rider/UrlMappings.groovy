package dn.rider

class UrlMappings {

    static mappings = {

        "/deliveryNotes/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/api/$controller/$action"()


        "/deliveryNotes/search"(controller: "searchDn")

        "/"(controller: 'home')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}