![Alt Text](https://i.ibb.co/c2HpvRK/front.jpg)

### Installation
Le serveur tourne de base sur le port 22000. \
Identifiant database non root, a spécifier pour installer dans application.properties en remplaçant SpringDatabase ligne 2 & 3

Homepage en / ou /index

### Lancement
Pour lancer le serveur, 3 possibilités:

* Utiliser le `start.bat` à la racine du projet
 
* Exécuter la commande suivante depuis la racine du projet:

    `java -jar ./target/restonsbrief-0.1.jar`

* Sinon, depuis l'emplacement du .jar dans le dossier target du projet:

    `java -jar restonsbrief-0.1.jar`

### Les différentes routes de l'API:

    GET /countries => La liste de tous les pays
    GET /country/{id} => Les informations du pays portant cet id
    POST /country => Enregistre un pays
    PUT /country/{id} => Met à jour un pays portant cet id
    DELETE /country/{id} => Supprime le pays portant cet id

### Techno && Informations
JQuery est utilisé pour traiter l'ajax, tout le reste en vanilla JS