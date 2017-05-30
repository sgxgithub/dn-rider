# RAF

## Domaines:

IC : pipeline de deploiement continue

API: API REST

WEB: IHM Web

## Elements

- [ ] [DOC] présenter les fonctionnalités implantées dans README.md

- [ ] [IC] pipeline de deploiement continue sur "epidural" vers "crisdorgames"

- [ ] [WEB] accès aux écrans par URL 

- [ ] [WEB] Tableau de Comparaison par selection d'un intervalle de versions

- [ ] [WEB] Editeur de NDL (avec validation et stockage)

- [ ] [API] Récupèrer une note de livraison au format json (GET /api/deliveryNotes/*APP*/*VERSION*)

- [ ] [API] Récupèrer la liste des note de livraison
    -  GET /api/deliveryNotes/*APP*
    -  GET /api/deliveryNotes/*APP*/releases (seulement les releases)
    -  GET /api/deliveryNotes/*APP*/snapshots (seulement les snapshots)
    -  format JSON ou Textuel selon parametre format (?format=json (default) OU ?format=text)

- [ ] [API] Récupèrer la liste des applications avec note de livraison (GET /api/applications)
    -  format JSON ou Textuel selon parametre format (?format=json (default) OU ?format=text)

- [ ] [API] Stocker une note de livraison.
    -  POST /api/deliveryNotes/*APP*/releases?version=*VERSION* (erreur si la version cible est une release deja existante)
    -  POST /api/deliveryNotes/*APP*/snapshots?version=*VERSION* (erreur si la version cible est une release deja existante)
    -  PUT /api/deliveryNotes/*APP*/*VERSION* (erreur si la version cible est une release deja existante)

- [ ] [API] Supprimer une note de livraison (DELETE /api/deliveryNotes/*APP*/*VERSION*)

- [ ] [API] Valider une note de livraison (POST /api/validations pour une note de livraison non stockée,  GET /api/validations/*APP*/*VERSION* pour une note de livraison deja stockée)
    -  validation selon schema http://gitlab.socrate.vsct.fr/rundep/katana/tree/dev/ndl_json-schema
