package dn.rider

class UrlMappings {

    static mappings = {

        "/deliveryNotes/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        //[API] Png
        "/api/ping"(controller: "deliveryNotes", action: "ping", method: "GET")

        //[API] Récupèrer la liste des applications avec note de livraison
        "/api/applications"(controller: "deliveryNotes", action: "showApps", method: "GET")

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

        //[API] Stocker une note de livraison
        // (pour l'instant mettre à jour si la version cible est une release deja existante)
        "/api/deliveryNotes/$app"(controller: "deliveryNotes", action: "saveDn", method: "POST")
        "/api/deliveryNotes/$app/releases"(controller: "deliveryNotes", action: "saveDn", method: "POST") {
            releaseType = 'releases'
        }
        "/api/deliveryNotes/$app/snapshots"(controller: "deliveryNotes", action: "saveDn", method: "POST") {
            releaseType = 'snapshots'
        }
        "/api/deliveryNotes/$app/$version"(controller: "deliveryNotes", action: "saveDn", method: "PUT") {
            releaseType = 'releases'
        }

        //[API] Supprimer une note de livraison (DELETE /api/deliveryNotes/*APP*/*VERSION*)
        "/api/deliveryNotes/$app/$version"(controller: "deliveryNotes", action: "deleteDn", method: "DELETE")

        //[API] Valider une note de livraison
        "/api/validations"(controller: "deliveryNotes", action: "validationNoStored", method: "POST")
        "/api/validations/$app/$version"(controller: "deliveryNotes", action: "validationStored", method: "GET")

        //[API] Autres appels pour extraire des infos des NDLs
        "/api/deliveryNotes/$app/$version/packages"(controller: "deliveryNotes", action: "getPackages", method: "GET")
        "/api/deliveryNotes/$app/$version/packages/$id"(controller: "deliveryNotes", action: "getPackage", method: "GET")

        "/"(controller: 'home')
        "500"(view: '/error')
        "404"(view: '/notFound')


        /*****************************************************************
         * SWAGGER DOC
         *****************************************************************/

        //[API] Ping
        "/apidoc/ping"(controller: "deliveryNotes", action: "ping")

        //[API] Récupèrer la liste des applications avec note de livraison
        "/apidoc/applications"(controller: "deliveryNotes", action: "showApps")

        //[API] Récupèrer la liste des note de livraison
        "/apidoc/deliveryNotes/$app"(controller: "deliveryNotes", action: "showVersions") {
            releaseType = 'all'
        }
        "/apidoc/deliveryNotes/$app/releases"(controller: "deliveryNotes", action: "showVersions") {
            releaseType = 'releases'
        }
        "/api/deliveryNotes/$app/snapshots"(controller: "deliveryNotes", action: "showVersions") {
            releaseType = 'snapshots'
        }

        //[API] Récupèrer une note de livraison au format json
        "/apidoc/deliveryNotes/$app/$version"(controller: "deliveryNotes", action: "showDn")

        //[API] Stocker une note de livraison
        "/apidoc/deliveryNotes/$app/releases"(controller: "deliveryNotes", action: "saveDn") {
            releaseType = 'releases'
        }
        "/apidoc/deliveryNotes/$app/snapshots"(controller: "deliveryNotes", action: "saveDn") {
            releaseType = 'snapshots'
        }

        //[API] Valider une note de livraison
        "/apidoc/validations"(controller: "deliveryNotes", action: "validationNoStored")
        "/apidoc/validations/$app/$version"(controller: "deliveryNotes", action: "validationStored")

        //[API] Supprimer une note de livraison (DELETE /api/deliveryNotes/*APP*/*VERSION*)
        "/apidoc/deliveryNotes/$app/$version"(controller: "deliveryNotes", action: "deleteDn")
    }
}