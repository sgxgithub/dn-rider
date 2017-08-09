# DN-Rider (Delivery Notes Rider)

Objectif: une application web (IHM + API REST) pour manipuler les notes de livraison Katana:

 * gérer l'objet NDL de manière plus simple qu'avec nexus/filesystème
 * éviter les tableaux de suivi, type notes d'installation / tableaux de dépendances / ..., édités manuellement
 * fédérer certaines fonctionnalités (extraction d'information, identification des package a installer sur une plateforme...) par rapport aux scripts bash/groovy/perl/ruby....
 * outiller le suivi du cycle de vie des versions par rapports aux infos remontées par les outils de l'usine logicielle et Katana

# Getting started

L'application peut être exploiter avec des commandes grails ou gradle.

## Lancer l'application
``` 
grails run-app
```

## Packager l'application
### créer un war
``` 
grails war
```
ou
``` 
gradle assemble
```
[Référence](http://mrhaki.blogspot.fr/2016/06/grails-goodness-creating-fully.html)

==> /build/libs

## Lancer les tests 
Les codes sources se situe dans grails-app/src/integration-test.
```
grails test-app
```

==> /build/reports/tests

## Proxy
Sur le serveur l'application passe par le proxy bluelagoon, 
En local il faut commenter les configuration associées dans gradle.properties pour télécharger les packages sans passer par bluelagoon.

 
 