<p align="center"><img src="readme/images/eseo_logo.png" width="400"></p>

# Projet Final Développement des applications client / serveur sous Android
> **Rendu de VALLET Antoine et MADUREIRA Hugo**

> *Dernière mise à jour le 14 juin 2021*

Dans le cadre de la matière *Développement des applications client / serveur sous Android* de notre cursus Ingénieur (Semestre 8), nous avons du développer en binôme une application en suivant **[un cahier des charges](#cahier-des-charges)** détaillés.

## Vidéo

Voici **[la vidéo]()** qui récapitule les fonctionnalités de l'application.

## Cahier des charges

> *Cette partie est un copier coller du **[TP à rendre](https://cours.brosseau.ovh/tp/android/app-avance-android.html)** de **[Valentin Brosseau](https://www.linkedin.com/in/valentin-brosseau-99b98827/)**.*

> ❌ = Je n'ai pas réussis ou eu le temps de réaliser la fonctionnalité
> 
> ✔️ = La fonctionnalité est complètement fonctionnel


L'application à fournir doit contenir au minimum les éléments suivants :


Serveur :

- ✔️ Stocker l'information dans une base de données.
- ✔️ Créer un client.
- ✔️ Récupérer les dernières données d'un client via son UUID / ID / TOKEN.
- ✔️ Bonus liste de l'ensemble des clients présents en base de données. (https://hidden-chamber-01030.herokuapp.com/public/api/devices)
- ✔️ Bonus obtention des données historiques d'un client via son UUID / ID / TOKEN. (https://hidden-chamber-01030.herokuapp.com/public/api/data?device_token=TOKEN)

Client :

- ✔️ Les données d'un client choisi (via saisie ou flash QRCode du code)
- ✔️ L'affichage des données du client devra indiquer l'horodatage de la dernière collecte.
- ✔️ Un bouton d'actualisation.
- ✔️ Possibilités d'affichage **(implémentation au choix)** :
  - ❌ Via une Recyclerview simple (mais avec des noms des capteurs dans la langue du client).
  - ✔️ Via un Layout dédié avec des icônes en fonction du type de capteurs (une carte OpenStreetMap peut-être utilisée pour la position GPS).
- ❌ Bonus la vue peut-être découpée en deux tabs afin d'avoir une vue listant l'ensemble des collecteurs de données connues par le serveur. Chaque ligne contiendra une action permettant d'afficher un « client précis » (donc sans connaitre son code préalablement).
- ❌ Bonus affichage des données historiques d'un client.
  
