package dn.rider

class UrlMappings {

    static mappings = {

        "/deliveryNotes/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

//        "/api/$controller/$action"()

        "/api/deliveryNotes/$app"(controller: "deliveryNotes", action: "showVersions", method: "GET") {
            releaseType = 'all'
        }

        "/api/deliveryNotes/$app/releases"(controller: "deliveryNotes", action: "showVersions", method: "GET") {
            releaseType = 'releases'
        }

        "/api/deliveryNotes/$app/snapshots"(controller: "deliveryNotes", action: "showVersions", method: "GET") {
            releaseType = 'snapshots'
        }

        "/api/deliveryNotes/$app/$version"(controller: "deliveryNotes", action: "showDn", method: "GET")
        "/api/applications"(controller: "deliveryNotes", action: "showApps", method: "GET")

        "/toto/dopost"(controller: 'toto', action: 'doPost')
        "/toto/doget/$name?"(controller: 'toto', action: 'doGet')

        "/"(controller: 'home')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}