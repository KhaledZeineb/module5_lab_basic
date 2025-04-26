1. Pourquoi la mise en cache est-elle importante dans les applications web ? Quels sont les compromis ?
✅ Avantages :

- Performances : Réduction des temps de réponse.

- Charge serveur : Moins de requêtes en base de données.

- Scalabilité : Meilleure gestion du trafic.

⚠ Compromis :

- Données périmées si le cache n'est pas invalidé.
- Complexité de gestion (invalidation, cohérence).
- Consommation mémoire (ex: Redis).

2. Quels problèmes pourraient survenir si nous ne gérons pas correctement notre cache (par exemple, lorsque les données sont mises à jour) ?
  
* Données obsolètes (ex: mise à jour non propagée).
* Incohérences (ex: cache partiellement mis à jour).
* Surcharge mémoire si le cache n'expire jamais.

3. Comment fonctionnent les différentes annotations de cache ?

@Cacheable : Stocke le résultat d’une méthode (ex: getBook).
@CachePut : Met à jour le cache sans bloquer l’exécution (ex: updateBook).
@CacheEvict : Supprime une entrée ou vide le cache (ex: deleteBook).


4. Que se passerait-il si nous n’utilisions pas une classe sérialisable avec Redis ?
   
- Erreur : NotSerializableException si l'objet ne peut pas être converti en binaire.
- Solution : Implémenter Serializable.

5. Comment pourrions-nous mettre en œuvre des stratégies de mise en cache plus complexes (différents TTL pour différents types de données, etc.) ?

- TTL différencié : Configurer des durées de cache variables (ex: 1h pour les livres, 24h pour les auteurs).

- Cache hiérarchique : Combiner cache mémoire (Caffeine) et cache distant (Redis).

- Conditions : Cacher uniquement si certains critères sont remplis (condition = "#id > 10").

