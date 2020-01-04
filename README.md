
# explorando-marte-api
Teste de Programação Elo7

## 1. Baixar
```shell
git clone https://github.com/Maddytec/explorando-marte-api.git
./run.sh
```
## 2. Executar
`$ cd explorando-marte-api`
`$ mvn spring-boot:run`

Requisitos para executar a API:
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3](https://maven.apache.org)

## 3. Como testar a API?

Após executar o item 2:
a) Acessar a URL: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

b) Criar o Planalto: 
[http://localhost:8080/swagger-ui.html#/Planalto/saveUsingPOST](http://localhost:8080/swagger-ui.html#/Planalto/saveUsingPOST)

- Exemplo do Planalto: 
	{
	  "coordenadaX": 10,
	  "coordenadaY": 10
	}

c) Criar o Sonda:
[http://localhost:8080/swagger-ui.html#/Sonda/saveUsingPOST_1](http://localhost:8080/swagger-ui.html#/Sonda/saveUsingPOST_1) 
- Exemplo da Sonda:
{
  "coordenadaX": 5,
  "coordenadaY": 5,
  "direcao": "N"
}

- Retorno 
{  "id":  1,
  "coordenadaX":  5, 
   "coordenadaY":  5,  
   "direcao":  "N" 
 }

d) Enviar comando para a sonda explorar Marte:
[http://localhost:8080/swagger-ui.html#/Sonda/explorarUsingPOST](http://localhost:8080/swagger-ui.html#/Sonda/explorarUsingPOST)
- Comando para exploração da sonda em Marte - comandoExplorar: MMRRMMLL 
-  Campo "sondaId" é código da sonda criada no item anterior: 1

## 3. License
Este código é open source.
