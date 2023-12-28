# Gestion de l'acces à la data pour le service Marketing

## Status

Accepted

## Context

La DB de transaction est beaucoup solicité. A chaque nouvelle transaction, un event passe par l'injecteur de transaction 
pour qu'il soit stocké dans la DB. Il y a une ecriture continue de transaction. 
Le service de Marketing lit dans cette DB 1 fois par jour. Ce service recupere toutes les transactions. 

## Decision

L'implémentation et le déploiement d'un pattern master-salve dans ce context à était décidé.

## Consequence

Il y a une consistence éventuelle mais nous navons pas un besoin strict d'avoir les dernieres transactions faite. Si toutes les transactions
des 3 dernieres minutes n'ont pas étaient sauvegarder dans le slave, ce n'est pas important. 

L'injecteur peut écrire dans master sans etre perturbé par le lecture de Marketing

Marketing peut lire sans etre perturbé par l'éciture de l'injecteur 