package dn.rider

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:"dn")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
