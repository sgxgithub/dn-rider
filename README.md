# DN-Rider (Delivery Notes Rider)

Objectif: une application web (IHM + API REST) pour manipuler les notes de livraison Katana:

 * gérer l'objet NDL de manière plus simple qu'avec nexus/filesystème
 * éviter les tableaux de suivi, type notes d'installation / tableaux de dépendances / ..., édités manuellement
 * fédérer certaines fonctionnalités (extraction d'information, identification des package a installer sur une plateforme...) par rapport aux scripts bash/groovy/perl/ruby....
 * outiller le suivi du cycle de vie des versions par rapports aux infos remontées par les outils de l'usine logicielle et Katana

# API REST

## Récupèrer une note de livraison au format json
GET /dn.rider.api/deliveryNotes/*APP*/*VERSION*

## Récupèrer la liste des note de livraison
GET /dn.rider.api/deliveryNotes/*APP*

GET /dn.rider.api/deliveryNotes/*APP*/releases (seulement les releases)

GET /dn.rider.api/deliveryNotes/*APP*/snapshots (seulement les snapshots)

format JSON ou Textuel selon parametre

## Récupèrer la liste des applications avec note de livraison
GET /dn.rider.api/applications

questions: comment extraire/cacher/stocker cette liste

## Stocker une note de livraison.
POST /dn.rider.api/deliveryNotes/*APP*/releases?version=*VERSION* (erreur si la version cible est une release deja existante)

POST /dn.rider.api/deliveryNotes/*APP*/snapshots?version=*VERSION* (erreur si la version cible est une release deja existante)

PUT /dn.rider.api/deliveryNotes/*APP*/*VERSION* (erreur si la version cible est une release deja existante)

## Supprimer une note de livraison
DELETE /dn.rider.api/deliveryNotes/*APP*/*VERSION*

Attendre avant d'implanter cet appel

## Valider une note de livraison
POST /dn.rider.api/validations

GET /dn.rider.api/validations/*APP*/*VERSION* (pour une note de livraison deja stockée)

validation selon schema http://gitlab.socrate.vsct.fr/rundep/katana/tree/dev/ndl_json-schema

## comparer 2 notes de livraison
GET /dn.rider.api/deliveryNoteComparisons/*APP1*/*VERSION1*/*APP2*/*VERSION2*

# Definition Of Done
Actions à réaliser obligatoirement avant de faire git push
 * faire relire le code par un tiers
 * faire valider par un tiers que la feature développée fonctionne comme attendu
 * valider la nouvelle fonction par un ou des tests
 * exécuter grails test-app
 
 
 
 