# API de agendamento de transações financeiras

## Sumário
- [Introdução](#intro)
- [Instalação e Execução da API](#steps)
    - [Requisitos mínimos de sistema](#sys)
    - [Instalação](#install)
    - [Comandos úteis](#commands)
    - [Execução](#exec)
        - [Especificação do objeto FinancialTransaction](#data-specs)
- [Tecnologias utilizadas](#tech)
- [Considerações gerais](#general)
    -  [Estrutura do projeto](#structure)

---

<div id='intro'/>

## Introdução

A execução deste projeto é realizada através do _Docker_ (engine de build dos ambientes) e do _docker-compose_ (arquivo "docker-compose.yml" na raiz do projeto), tendo seus gatilhos disparados pelo _Make_ (arquivo "Makefile" na raiz do projeto) para fins de agilidade na gestão da API.

O emprego destas tecnologias auxilia na prevenção de diferenças entre ambientes (dev, hlg, stg e prd). Para construção da API foi utilizada a linguagem de programação Java (v11) com o framewor Spring Boot pois contém tratativas padrões para gestão de requisições contribuem para o foco na coesão da solução, além de fornecer uma estrutura excelente para padronização da aplicação e muitas outras funcionalidades.

Na construção, foi empregado o uso do design patter MVC (com a camada de modelo suprimida, pois a persistência é feita em memória durante a execução da aplicação. Esta foi simbólicamente subistituida pela classe do tipo Repositório, simluando o uso de JPA).

Uma listagem completa das tecnologias utilizadas e suas respectivas funções pode ser encontrada [aqui](#tech).

---

<div id='steps'/>

## Instalação e Execução da API

<div id='sys'/>

### Requisitos mínimos de sistema:
```yaml
- GNU Make v4.1 (ou superior)
- Docker v18.09.6 (ou superior)
- docker-compose v1.22.0 (ou superior)
```

<div id='install'/>

### Instalação:

> Nesta etapa será feito o download das imagens Docker do Maven (gestor de dependências/build) e do Java (openjdk-11). Este processo poderá demorar alguns minutos. Após o término do download das dependências e do build a API estará de pé conforme as configurações do arquivo ".env" (na raíz deste repositório) onde, diante da necessidade, poderão ser trocados a máscara de rede da API, seu IP e a PORTA MAPEADA do localhost.

1. Faça download (ou clone) deste repositório:
    ```shellscript
        git clone https://github.com/brusalves/financial-transactions-scheduler
    ```
2. Altere a opção de versionamento e o permissionamento do diretório clonado:
    ```shellscript
        cd financial-transactions-scheduler && \
        git config core.filemode false && \
        chmod 777 -R .
    ```
3. Dentro do diretório clonado, inicie a aplicação:
    ```shellscript
        make start
    ```

<div id='commands'/>

### Outros comandos úteis:

- Reinicialização da aplicação:
    ```shellscript
        make restart
    ```

- Desligamento da aplicação:
    ```shellscript
        make stop
    ```

- Visualização de LOG's:
    ```shellscript
        make logs
    ```

- Visualização de status da aplicação:
    ```shellscript
        make status
    ```

> Observação 1: Se forem feitas alterações no arquivo ".env" a aplicaçãp deverá ser reiniciada.

> Observação 2: O reinicio da aplicação implica no "reset" dos dados persistidos em memória.

<div id='exec'/>

### Execução:

> Observação: [Especificação do objeto FinancialTransaction](#data-specs)

- Criação de um agendamento:
    ```shellscript
    curl -X POST \
        http://localhost:8080/financial-transactions/schedules \
        -H 'Content-Type: application/json' \
        -d '{
            "transactionDate": "{{transactionDate}}",
            "account": "{{account}}",
            "destination": "{{destination}}",
            "value": {{value}}
        }'
    ```

- Listagem de um agendamento:
    ```shellscript
    curl -X GET http://localhost:8080/financial-transactions/schedules/{{id}}
    ```

- Listagem de agendamentos:
    ```shellscript
    curl -X GET http://localhost:8080/financial-transactions/schedules
    ```

- Atualização de agendamentos:
    ```shellscript
    curl -X PUT \
        http://localhost:8080/financial-transactions/schedules/{{id}} \
        -H 'Content-Type: application/json' \
        -d '{
            "transactionDate": "{{transactionDate}}",
            "account": "{{account}}",
            "destination": "{{destination}}",
            "value": {{value}}
        }'
    ```

- Remoção de agendamentos:
    ```shellscript
    curl -X DELETE http://localhost:8080/financial-transactions/schedules/{{id}}
    ```

<div id='data-specs'/>

#### Especificação do objeto FinancialTransaction
```json
{
    // Identificador (String) | Gerado pelo sistema
    "id": "d8bca22c-3595-4186-babb-458efbf2be7f",
    // Custo da operação (BigDecimal) | Formato: 0.00 | Gerado pelo sistema
    "operationCost": 6,
    // Data do agendamento (Date) | Formato: yyyy-MM-dd'T'hh:mm:ss'Z' | Gerado pelo sistema
    "scheduleDate": "2019-07-17T19:12:01.566+0000", 
    // Conta origem (String) | Informada pelo usuário
    "account": "123456789",
    // Conta destino (String) | Informada pelo usuário
    "destination": "987654321",
    // Valor transferido (BigDecimal) | Formato: 0.00 | Informada pelo usuário
    "value": 100,
    // Data da transação (Date) | Formato: yyyy-MM-dd'T'hh:mm:ss'Z' | Informada pelo usuário
    "transactionDate": "2019-07-18T12:00:00.000+0000" 
}
```

> Observação: durante a criação/atualização de uma nova transferência, os campos "id", "operationCost" e "scheduleDate" serão ignorados se informados no corpo da requisição.

---

<div id='tech'/>

## Tecnologias utilizadas

---

<div id='general'/>

## Considerações gerais

<div id='structure'/>

### Estrutura do projeto

```
➜  financial-transactions-scheduler git:(master) tree
.
├── app
│   ├── modules
│   │   └── financial-transactions-scheduler
│   │       ├── build
│   │       ├── pom.xml
│   │       └── src
│   │           ├── main
│   │           │   ├── java
│   │           │   │   └── dev
│   │           │   │       └── brunoalves
│   │           │   │           └── financialtransactionsscheduler
│   │           │   │               ├── business
│   │           │   │               │   └── OperationCost.java
│   │           │   │               ├── controller
│   │           │   │               │   └── ScheduleController.java
│   │           │   │               ├── FinancialTransactionsSchedulerApplication.java
│   │           │   │               ├── model
│   │           │   │               │   └── FinancialTransaction.java
│   │           │   │               ├── repository
│   │           │   │               │   └── FinancialTransactionRepository.java
│   │           │   │               ├── service
│   │           │   │               │   └── ScheduleService.java
│   │           │   │               └── util
│   │           │   │                   ├── DateDiff.java
│   │           │   │                   └── FinancialTransactionValidator.java
│   │           │   └── resources
│   │           │       └── application.properties
│   │           └── test
│   │               └── java
│   │                   └── dev
│   │                       └── brunoalves
│   │                           └── financialtransactionsscheduler
│   │                               ├── business
│   │                               │   └── OperationCostTest.java
│   │                               ├── FinancialTransactionsSchedulerApplicationTests.java
│   │                               └── service
│   │                                   └── ScheduleServiceTest.java
│   └── resources
│       ├── maven
│       └── tomcat
├── bin
│   └── jar
├── docker-compose.yml
├── financial-transactions-scheduler.postman_collection.json
├── LICENSE
├── Makefile
└── README.md

29 directories, 17 files
```
> Observação: para análise do código do projeto, a workspace da IDE pode ser configurada em: "./app/modules/financial-transactions-scheduler/"