# SPPerson
> App para cadastro de pessoas

## Docker
Tens a opção de subir o app usando o **docker-compose**, basta baixar o arquivo **docker-compose.yml** (raiz do repositório) e por linha de comando executar `docker-compose up` <br>
Obs: as imagens necessárias serão baixadas diretamente do DockerHub

## Links
- Coleção de requisições e variáveis do Postman: [Link](https://github.com/marcosviniciusam90/spperson/tree/master/backend/doc)

### Aplicação (frontend) em produção
- Endereço: https://mvam-spperson.netlify.app

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

<h2 id="autorizacao">Como obter autorização e acesso à aplicação</h2>

Informe os dados:
- Usuário/Senha: `marcos@gmail.com/marcos` ou `admin@gmail.com/admin`

No swagger, clique no botão **Authorize**, além de usuário e senha, deverá ser informado também:
- client_id/client_secret: **angular/angular123**<br/><br/>
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

## No frontend 
- Angular 11
- JavaScript/TypeScript
- HTML
- CSS
- PrimeNG 11
- NPM