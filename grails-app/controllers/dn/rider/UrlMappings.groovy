package dn.rider

class UrlMappings {

    static mappings = {

        "/deliveryNotes/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

//        get "/api/deliveryNotes/$app/releases"(controller: "deliveryNotes", action: "showVersions", params: [releaseType: 'releases'])
//        get "/api/deliveryNotes/$app/snapshots"(controller: "deliveryNotes", action: "showVersions", params: [releaseType: 'snapshots'])

        get "/api/deliveryNotes/$app/versions/$version"(controller: "deliveryNotes", action: "showDn")
        get "/api/deliveryNotes/$app/$releaseType?"(controller: "deliveryNotes", action: "showVersions")
        get "/api/applications"(controller: "deliveryNotes", action: "showApps")

        "/deliveryNotes/search"(controller: "searchDn")

        "/"(controller: 'home')
        "500"(view: '/error')
        "404"(view: '/notFound')

    }
}