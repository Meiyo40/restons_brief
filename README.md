###Installation
Le serveur tourne de base sur le port 22000. \
Identifiant database non root, a spécifier pour installer dans application.properties en remplaçant SpringDatabase ligne 2 & 3

Homepage en / ou /index

###Les différentes routes de l'API:

    GET /countries => La liste de tous les pays
    GET /country/{id} => Les informations du pays portant cet id
    POST /country => Enregistre un pays
    PUT /country/{id} => Met à jour un pays portant cet id
    DELETE /country/{id} => Supprime le pays portant cet id

###Techno && Informations
JQuery est utilisé pour traiter l'ajax, tout le reste en vanilla JS