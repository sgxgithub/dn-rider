package dn.rider

class UrlMappings {

    static mappings = {

        "/deliveryNotes/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        //[API] Récupèrer la liste des note de livraison
        "/api/deliveryNotes/$app"(controller: "deliveryNotes", action: "showVersions", method: "GET") {
            releaseType = 'all'
        }
        "/api/deliveryNotes/$app/releases"(controller: "deliveryNotes", action: "showVersions", method: "GET") {
            releaseType = 'releases'
        }
        "/api/deliveryNotes/$app/snapshots"(controller: "deliveryNotes", action: "showVersions", method: "GET") {
            releaseType = 'snapshots'
        }

        //[API] Récupèrer une note de livraison au format json (GET /api/deliveryNotes/*APP*/*VERSION*)
        "/api/deliveryNotes/$app/$version"(controller: "deliveryNotes", action: "showDn", method: "GET")

        //[API] Récupèrer la liste des applications avec note de livraison
        "/api/applications"(controller: "deliveryNotes", action: "showApps", method: "GET")

        //[API] Valider une note de livraison
        "/api/validations"(controller: "deliveryNotes", action: "validationNoStored", method: "POST")
        "/api/validations/$app/$version"(controller: "deliveryNotes", action: "validationStored", method: "GET")

        "/"(controller: 'home')
        "500"(view: '/error')
        "404"(view: '/notFound')

    }
}