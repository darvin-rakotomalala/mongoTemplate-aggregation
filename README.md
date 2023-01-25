## Spring Data MongoDB | MongoTemplate Aggregation
Dans ce repo, nous allons voir comment implémenter des agrégations MongoDB dans une applications Spring Boot à l'aide de MongoTemplate.

### Technologies
---
- Java 11
- Spring Boot 2.7.7
- Spring Data Mongodb
- Lombok
- MapStruct
- Maven 3+
- Mongodb 5 / Robo 3T

### Exemples
---
- Aggregation Match
- Aggregation Limit
- Aggregation Project
- Aggregation Skip
- Aggregation Group
- Aggregation replaceRoot
- Aggregation Conditionals
- Aggregation Unwind
- Aggregation addToSet
- Aggregation Sum avec Group
- Aggregation First avec Group

### Exécuter et tester le projet
---
- Exécuter l'application `mvn spring-boot:run`
- Importer dans Postman la collection `mongoTemplate-aggregation.postman_collection.json` pour tester les APIs
- Vous pouver utiliser aussi l'url du Swagger ou importer l'url Swagger dans Postman
  - Les descriptions OpenAPI seront disponibles au chemin `/v3/api-docs` par défaut : http://localhost:8081/v3/api-docs/ et/ou http://localhost:8081/swagger-ui/index.html