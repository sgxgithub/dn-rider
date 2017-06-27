package dn.rider

class UrlMappings {

    static mappings = {

        "/deliveryNotes/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

//        "/api/$controller/$action"()

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

//        [API] Stocker une note de livraison
//        -  POST /api/deliveryNotes/*APP*/releases?version=*VERSION* (erreur si la version cible est une release deja existante)
        "/api/deliveryNotes/saveDn"(controller: "deliveryNotes", action: "saveDn", method: "POST")

        "/toto/dopost"(controller: 'toto', action: 'doPost')
        "/toto/doget/$name?"(controller: 'toto', action: 'doGet')

        "/"(controller: 'home')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}