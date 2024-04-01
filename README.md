# Subscriber-bc

## Description

Subscriber-bc est une application de gestion des abonnés.

Elle expose une API REST permettant de créer, mettre à jour et résilier les abonnés.
Il s'agit également d'une application stateless, les données seront stockées dans une base de données 
(Voir les flux entrants/sortants pour plus de détails).

## Flux entrant(s)

| Application              | Protocol | Description                                |
|--------------------------|----------|--------------------------------------------|
| Postman (tests en local) | HTTP     | Appel de l'API REST de gestion des abonnés |

## Flux sortant(s)

| Application | Protocol | Description                                      |
|-------------|----------|--------------------------------------------------|
| PostgreSQL  | HTTP     | Création, mise à jour et résiliation des abonnés |
