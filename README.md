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

## Base de données

Les abonnés (Subscriber) sont stockés dans la table "subscriber" 
au sein de la base PostgreSQL.

| Colonne       | Type         | Description                                     |
|---------------|--------------|-------------------------------------------------|
| subscriber_id | UUID         | Id d'un abonné (Clé primaire)                   |
| firstname     | VARCHAR(100) | Prénom d'un abonné                              |
| lastname      | VARCHAR(200) | Nom de famille d'un abonné                      |
| email         | VARCHAR(200) | Email d'un abonné (clé unique)                  |
| phone         | CHAR(10)     | Numéro de téléphone sans indicatif (clé unique) |
| enabled       | BOOLEAN      | Indique si un abonné est actif                  |

## Fonctionnalités

| Endpoint REST                  | Description                                                                                     |
|--------------------------------|-------------------------------------------------------------------------------------------------|
| POST /subscribers              | Création d'un abonné                                                                            |
|                                | - Le request body est soumis à des conditions de validation en fonction du format attendu.      |
|                                | - Si l'email ou le numéro de téléphone (phone) sont déjà répertoriés, un code 409 est retourné. |
| GET /subscribers/{id}          | Récupérer un abonné à partir de son id                                                          |
|                                | - Lorsque l'id de l'abonné n'est pas répertorié, un 404 est retourné.                           |
| POST /subscribers/{id}/disable | Résilier un abonné (sans le supprimer physiquement)                                             |
|                                | - Lorsque l'id de l'abonné n'est pas répertorié, un 404 est retourné.                           |
| PATCH /subscribers/{id}        | Mettre à jour les données d'un abonné                                                           |
|                                | - Le request body est soumis à des conditions de validation en fonction du format attendu.      |
|                                | - Si l'email ou le numéro de téléphone (phone) sont déjà répertoriés, un code 409 est retourné. |

## Choix d'implémentation

### Architecture en couches

| Présentation              | Métier                 | Accès aux données |
|---------------------------|------------------------|-------------------|
| API REST (Spring Web MVC) | - Entités JPA          | Spring Data JPA   |
|                           | - Classe(s) de service |                   |

## Tester l'application en local

1) Lancer le build du container Docker (hébergeant la base de données PostgreSQL).

`docker compose up`

2) Lancer le build Maven.
Remarque: Le pom.xml est configuré pour Java 21 et Spring Boot 3.

`mvn clean install`

3) Créer automatiquement la table "subscriber" à partir du schéma Liquibase :

`mvn liquibase:update`

4) Démarrer l'application avec `SubscriberBcApplication`.

5) Des fichiers Postman sont mis à disposition pour tester l'application :

- `src/main/resources/postman/collection`
- `src/main/resources/postman/env`

