# Catálogo de Produtos
Este é um projeto de um catálogo de produtos simples utilizando Spring Boot.


## Tecnologias utilizadas

* Java 8
* Spring Boot 2.7.10
* Spring Data JPA
* MySQL
* MapStruct
* Spring Boot Validation
* Springfox Swagger
* JUnit 5
* Mockito
* Docker

## Configuração do banco de dados
Antes de executar a aplicação, é necessário criar o banco de dados e configurar o acesso no arquivo application.properties, localizado na pasta src/main/resources.
### Docker composer
O projeto já vem com a configuração do banco de dados no docker, basta executar o seguinte comando na raiz do projeto:
    
    docker-compose up

### application.properties
Configuração do banco de dados no application.properties

    spring.datasource.url=jdbc:mysql://localhost:3306/products?serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=product

## Executando a aplicação
Para executar a aplicação, basta executar o seguinte comando na raiz do projeto:

    ./gradlew bootRun

## Documentação
A documentação da API pode ser acessada através do Swagger, disponível em http://localhost:8085/swagger-ui/.
### Collection
A collection do Postman para testar as rotas da API está disponível na pasta /doc do projeto. Para utilizá-la, basta importar o arquivo "Product-MS.postman_collection.json" no aplicativo do Postman e as rotas estarão prontas para serem testadas.

## Testes
Os testes unitários da aplicação foram implementados com JUnit 5 e Mockito e podem ser executados com o seguinte comando:

    ./gradlew jacocoTestCoverageVerification

A cobertura de testes pode ser obtida através do relatório gerado pelo JaCoCo, que pode ser encontrado em build/reports/jacocoHtml/index.html.