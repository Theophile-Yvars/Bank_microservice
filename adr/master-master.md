# Passage de PostgreSQL à Cassandra avec la réplication master-master pour la Gestion des Données de Transactions Bancaires

## Status

Accepté

## Context

Dans l’architecture de PrimaBank, nous utilisons actuellement PostgreSQL pour stocker les données de transactions bancaires. Cependant, face à l'augmentation du volume de transactions et aux exigences de performances élevées, nous rencontrons des limitations avec PostgreSQL, particulièrement en termes de montée en charge et de performance.
Considération des Options:

### Continuer avec PostgreSQL:
Avantages : Familiarité avec la technologie, intégration existante, ACID compliance.
Inconvénients : Limites de montée en charge, performances sous forte charge, difficultés de réplication multi-maîtres.
### Migration vers Cassandra avec réplication master-master:
Avantages : Haute disponibilité, excellente montée en charge horizontale, réplication master-master native, adapté pour de grandes quantités de données et une haute vélocité de transactions.
Inconvénients : Complexité accrue, modèle de données NoSQL, cohérence éventuelle (Eventual Consistency).


## Decision

Nous avons décidé de migrer notre système de gestion des données de transactions de PostgreSQL à Cassandra. Cette décision est motivée par les besoins suivants :

**Montée en charge horizontale :** Cassandra offre une meilleure montée en charge horizontale, ce qui est essentiel pour gérer l'augmentation du volume de transactions.
**Haute disponibilité :** La réplication master-master de Cassandra assure une haute disponibilité des données, minimisant ainsi le risque de perte de données ou de temps d'arrêt.
**Performances sous forte charge :** Cassandra est conçu pour maintenir des performances élevées même sous des charges de travail intenses, ce qui est crucial pour les opérations bancaires.

## Consequences

La migration vers Cassandra nécessitera une refonte de notre modèle de données et une adaptation de nos microservices à l'écosystème NoSQL. Cela impliquera également une formation de notre équipe au fonctionnement de Cassandra et à ses meilleures pratiques.
