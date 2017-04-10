# DN-Rider (Delivery Notes Rider)

Objectif: une application web (IHM + API REST) pour manipuler les notes de livraison Katana:

 * gérer l'objet NDL de manière plus simple qu'avec nexus/filesystème
 * éviter les tableaux de suivi, type notes d'installation / tableaux de dépendances / ..., édités manuellement
 * fédérer certaines fonctionnalités (extraction d'information, identification des package a installer sur une plateforme...) par rapport aux scripts bash/groovy/perl/ruby....
 * outiller le suivi du cycle de vie des versions par rapports aux infos remontées par les outils de l'usine logicielle et Katana

# API REST
## GET /api/deliveryNotes/*APP*
Récupère la liste des note de livraison  (format JSON ou Textuel)

## GET /api/deliveryNotes/*APP*/*VERSION*
Récupère une note de livraison au format json

## POST /api/deliveryNotes/*APP*
Stocke une note de livraison.

## DELETE /api/deliveryNotes/*APP*/*VERSION*
Supprime une note de livraison

# Definition Of Done
Actions à réaliser obligatoirement avant de faire git push
 * faire relire le code par un tiers
 * faire valider par un tiers que la feature développée fonctionne comme attendu
 * valider la nouvelle fonction par un ou des tests
 * exécuter grails test-app
 
 
 
 