# RAF

## Domaines:

IC : pipeline de deploiement continue

API: API REST

WEB: IHM Web

## Elements

- [ ] [DOC] présenter les fonctionnalités implantées dans README.md

- [ ] [IC] pipeline de deploiement continue sur "epidural" vers "crisdorgames"

- [ ] [IC] tests automatiques de non-regressions

- [ ] [WEB] accès aux écrans par URL 

- [ ] [WEB] Consolidation page de Comparaison (ex: selection d'un intervalle de versions, toutes les versions)

- [ ] [WEB] Editeur de NDL (avec validation et stockage)

- [ ] [WEB/API] swagger

- [ ] [API] Récupèrer une note de livraison au format json (GET /api/deliveryNotes/*APP*/*VERSION*)

- [ ] [API] Récupèrer la liste des note de livraison
    -  GET /api/deliveryNotes/*APP*
    -  GET /api/deliveryNotes/*APP*/releases (seulement les releases)
    -  GET /api/deliveryNotes/*APP*/snapshots (seulement les snapshots)
    -  format JSON ou Textuel selon parametre format (?format=json (default) OU ?format=text)

- [ ] [API] Récupèrer la liste des applications avec note de livraison (GET /api/applications)
    -  format JSON ou Textuel selon parametre format (?format=json (default) OU ?format=text)

- [ ] [API] Stocker une note de livraison.
    -  POST /api/deliveryNotes/*APP*?version=*VERSION*[&type=*RELEASE|SNAPSHOT*][&repositoryId=*REPO*]
       (type est optionnel: Si pas fourni, RELEASE sauf si syntaxe  type snapshot: ex xxx-SNAPSHOT ; erreur si valeur invalide)
       (si type RELEASE et donnée déjà présente -> erreur)
       (repositoryId est optionnel et doit correspondre a un repo conforme au type: si pas fourni: identifier le repo unique adéquat, si pas trouvé ou plusieurs solution -> erreur; erreur si repository inexistant)    
    -  POST /api/deliveryNotes/*APP*/releases?version=*VERSION*[&repositoryId=*REPO*] = POST /api/deliveryNotes/*APP* avec type=RELEASE
    -  POST /api/deliveryNotes/*APP*/snapshots?version=*VERSION*[&repositoryId=*REPO*] = POST /api/deliveryNotes/*APP* avec type=SNAPSHOT
    -  PUT /api/deliveryNotes/*APP*/*VERSION*
       - Si la version n'existe pas: stockage dans le repository NEXUS correspondant à l'appli, si possible de l'identifier de manière unique (si version=xxx-SNAPSHOT, prendre un prendre snapshot, sinon prendre un repo release)
       - Si la version existe: stockage/mise a jour dans le repository NEXUS correspondant (si identique a l'objet déjà stocké, ne pas tenter de le restocker)

- [ ] [API] Supprimer une note de livraison (DELETE /api/deliveryNotes/*APP*/*VERSION*)

- [ ] [API] Valider une note de livraison (POST /api/validations pour une note de livraison non stockée,  GET /api/validations/*APP*/*VERSION* pour une note de livraison deja stockée)
    -  validation selon schema http://gitlab.socrate.vsct.fr/rundep/katana/tree/dev/ndl_json-schema

- [ ] [API] Autres appels pour extraire des infos des NDLs
    - GET /api/deliveryNotes/*APP*/*VERSION*/packages: ramene la liste des identifiants de packages de la note de livraison
      (?format=json (default) -> tableau json OU ?format=text -> liste d'identifiant)
      les identifiants doivent permettre de retrouver chaque package individuel: *packageName(sansNumeroDeVersion)*#*module* ou *packageName(sansNumeroDeVersion)*#*module*#*techno* (s'il y a plusieurs module/packages sur des technos séparées)
    - GET /api/deliveryNotes/*APP*/*VERSION*/packages/*ID*: ramene les infos d'un package individuel
      avec ID=*packageName* ou *packageName*#*module* ou *packageName*#*techno*#*techno*
      (?format=json (default) OU ?format=text)
      si format=json: ramener l'objet JSON complete
      si format=text: 1 ligne par valeur (ex:MODULE=XXX pour les attributs de niveau 0 ou CHECKAFTERINSTALL_<index>_EXPECTEDHTTPCODE=200 pour les sous-elements en tableau)

