# Aplicação de contagem de votos

##Tools

- SpringBoot
- H2 Database
- JUnit | Mockito
- Swagger

## Dependências
Para mais detalhes das dependências utilizadas na aplicação basta olhar o pom.xml.

## Build do projeto
Você vai precisar:

- OpenJDK 11 or higher
- Maven 3.1.1 or higher
- Git
- Docker

Clone o projeto e utilize o maven para contruí-lo

	$ mvn clean install
	
## Executando o projeto

Com o projeto local há duas possíbilidades de execução

### 1:

	$ mvn package && java -jar ./target/vote-1.0.0.jar


### 2:

	$ docker build -t vote:1.0.0 .
    $ docker run -p 8080:8080 vote:1.0.0

## Documentação

A documentação pode ser acessada localmente em http://localhost:8080/api/swagger-ui

## Base de dados

A base de dados pode ser acessada localmente em http://localhost:8080/api/h2-console
