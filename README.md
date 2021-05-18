# SPPerson
> API REST para cadastro de pessoas

## Links
- Coleção de requisições do Postman: [Link](https://github.com/marcosviniciusam90/spperson/blob/master/doc/SPPerson.postman_collection.json)
- Imagem docker API: em breve

### API em produção
- Endereço: https://mvam-spperson.herokuapp.com
- Banco de dados: https://mvam-spperson.herokuapp.com/h2-console  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JDBC URL: `jdbc:h2:mem:testdb`
- Swagger JSON: https://mvam-spperson.herokuapp.com/api-docs
- Swagger UI: https://mvam-spperson.herokuapp.com/swagger-ui/index.html?url=/api-docs

### API local
- Endereço: http://localhost:8080
- Banco de dados: http://localhost:8080/h2-console  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JDBC URL: `jdbc:h2:mem:testdb`
- Swagger JSON: http://localhost:8080/api-docs
- Swagger UI: http://localhost:8080/swagger-ui/index.html?url=/api-docs

## O que foi utilizado?
- Java
- Spring
- REST
- Hibernate/JPA (Query methods, controle de transação, etc)
- H2 Database
- MapStruct (para converter entidade <-> DTO)
- Lombok
- Bean Validation
- Exceptions Handler
- Spring Security (Basic e OAuth2 + JWT com controle de permissões)
- Spring Events
- Spring Profiles (test, basic-security/oauth-security)
- Spring OpenAPI (Swagger UI)