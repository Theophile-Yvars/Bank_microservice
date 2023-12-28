# Gestion de plusieurs implémentations du services de marketing

## Status

Accepted

## Context

Nous avons plusieurs implémentations du service marketing. Mais un seul d'entre 
eux doit être démaré dans la stack docker. 
De plus, chaque service marketing gere la sauvegarde des données et la reception des 
events kafka. 

## Decision

### Séparation de la gestion des données des services de Marketing

Ajout d'un nouveau service, qui aura le rôle d'un injecteur. C'est ce service qui consommera les datas de kafka
pour les stocker dans la DB. Ainsi, les services marketing n'auront qu'a faire de la lecture quand ils en auront besoin.

### Gestion des differents services Marketings

L'implementation desiré, qui correspond à un des services Marketing, sera
scale à 1, tandis que les autres serons scale à 0. Un script bash permet de
choisir l'implémentation souhaité. Ainsi, le il sera facile de changé d'implémentation.
Puisque la consomation des events kafka et la sauvegarde de ces events est délégué à un autre service, cf le point au dessus, 
la manipulation des instances de marqueting n'a aucune influence dans le cycle de vie des events de transaction. 


## Consequences

Il est beaucoup plus facile de déployer de nouvelles implémentations de Marketing sans que le 
fonctionnement de l'application soit affecté. 
Puisque les services de Marketing n'ont plus à gérer la captation des events et de la sauvegarde de ces events, 
l'implémentation de ces services Marketings et leurs déploiement est plus simple est sécrue. 
Secure car il n'y a pas de gestion d'event entre le déploiement d'un service de marketing à un autre. 

Par contre, a chaque nouvelle implémentation d'un service marketing, il est necessaire de modifier le script de selection de Marqueting.
Il est necessaire d'ajouter l"existance du nouveau serivce pour que ca selection puisse être possible. 