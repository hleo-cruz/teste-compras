# Teste-compras

### Api construida usando as seguintes tecnologias:
* Java versao 21
* Spring boot versao 3
* Documenntação Swagger

### Api com o propósito de apresentar conhecimentos solicitados nas tecnologias acima.
# Lista de endpoints

Verbo Http   | Endpoint                              | Funcionalidade   |
--------- |---------------------------------------|------------------|
GET | **compras**                           | Listar historico de compras
GET | **compras/recomendacao/cliente/tipo** | Listar recomendacao de compra
GET | **compras/maior-compra/{ano}**        | Buscar maior compra com base no parametro ano
GET | **compras/clientes-fieis**            | Listar top 3 clientes

# Instruções para rodar o projeto
1. Clonar o repositório
2. Em uma linha de comando executar os comandos abaixo dentro do diretório do repósitorio clona
3. Executar o comando mvn clean
4. Para executar o projeto mvn spring-boot:run
5. [Clique aqui](http://localhost:8080/swagger-ui/index.html)