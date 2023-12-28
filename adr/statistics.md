
# Utilisation des transactions pour les calculs des statistiques

Date: 01/12/2023

## Statut

Accepted

## Contexte

1) Incohérence de données lors des calculs des statistiques : 
L'ancien modèle des statistiques se reposait sur le stockage des transactions dans une base de données séparée. Ensuite, un bash job se lancait chaque jour pour récupérer ces transactions et faire les calculs des statistiques. Le problème dans cette approche est l'obligation de gérer le temps de sauvegarde des transactions, plus précisement on devait supprimer chaque transaction qui dépasse 3 mois pour effacer les données non significatives. Cette approche nous ajoutaient une complexité dans la gestion des statistiques.

## Décision

Le changement proposé est d'avoir un calcul de statistiques en temps réel. Par conséquent, nous avons décidé de remplacer la base de données des statistiques (PostgreSQL) par une base de donnée clé-valeur (Redis) qui va contenir le nom de la statistique comme clé et un compteur comme valeur. Chaque transaction effectué va nous mener à incrémenter le compteur de chaque statistique. Nous envisageons d'avoir une replication Master-Master pour cette base de donnée pour supporter la charge d'écriture.

## Conséquences

- Pas besoin de gérer les durées de vies des transactions pour calculer les statistiques.
- Le service Stats est sollicité à chaque transaction, ce qu'on peut résoudre avec la scalabilité horizontale du service Stats.
- Gestion plus facile des statistiques grace au format clé-valeur.
