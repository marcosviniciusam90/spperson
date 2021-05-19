# SPPerson
> API REST para cadastro de pessoas

## Links
- Coleção de requisições do Postman: [Link](https://github.com/marcosviniciusam90/spperson/blob/master/doc/SPPerson.postman_collection.json)
- Imagem docker API: https://hub.docker.com/r/marcosviniciusam90/spperson

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

## <a name="autorizacao"></a>Como obter autorização para realizar requisições pelo Swagger?
No swagger, quando a aplicação estiver rodando com OAuth2, clique no botão **Authorize** e informe os dados:
- username/password: **marcos/marcos** ou **admin/admin**
- client_id/client_secret: **react/react123**<br/><br/>
Obs: o usuário **marcos** só tem permissão para buscar informações (GET), já o usuário **admin** possui todas as permissões.

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
- Spring MockMvc (para testar requisições REST)
- Mockito