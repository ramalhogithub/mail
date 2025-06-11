# Mail POC (Proof of Concept)

Este projeto é um *Proof of Concept* (POC) para demonstrar a implementação de um serviço de envio de e-mails em Spring Boot, utilizando o padrão **Adapter Factory** para permitir a integração flexível com diferentes provedores de serviços de e-mail (como AWS SES e OCI Email Delivery) sem modificar a lógica central da aplicação.

## Sumário

* [Visão Geral](#visão-geral)
* [Tecnologias Utilizadas](#tecnologias-utilizadas)
* [Estrutura do Projeto](#estrutura-do-projeto)
* [Pré-requisitos](#pré-requisitos)
* [Configuração do Projeto](#configuração-do-projeto)
* [Executando a Aplicação](#executando-a-aplicação)
* [Testando a API](#testando-a-api)
* [Executando os Testes Unitários](#executando-os-testes-unitários)
* [Licença](#licença)

## Visão Geral

O objetivo principal deste POC é abstrair a complexidade de diferentes SDKs de e-mail por trás de uma interface unificada. A aplicação expõe um único endpoint REST para envio de e-mails, e, em tempo de execução, decide qual adaptador de serviço de e-mail utilizar com base em uma propriedade de configuração.

**Características Principais:**

* **API REST:** Endpoint para envio de e-mails com validação de entrada.
* **Padrão Adapter Factory:** Seleção dinâmica do adaptador de e-mail (e.g., AWS, OCI) em tempo de execução.
* **Validação de Dados:** Utiliza `jakarta.validation` para garantir a integridade dos dados de entrada.
* **Tratamento de Erros:** Respostas padronizadas para erros de validação (400 Bad Request) e erros internos do servidor (500 Internal Server Error).
* **Logging:** Logs informativos para acompanhar o fluxo da requisição.

## Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.3.1**
* **Maven**
* **Lombok**
* **Jackson** (para serialização/desserialização JSON)
* **JUnit 5** (para testes unitários)
* **Mockito** (para mocking em testes)

## Pré-requisitos

Antes de executar este projeto, certifique-se de ter instalado:

* **Java Development Kit (JDK) 21** ou superior.
* **Maven 3.x** ou superior.
* Um editor de código/IDE, como IntelliJ IDEA ou VS Code (com extensões Java).
* Um cliente HTTP para testar a API (e.g., Postman, Insomnia, cURL).

## Configuração do Projeto

1.  **Clone o repositório** (se estiver em um sistema de controle de versão):
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    cd mail
    ```
2.  **Configuração da Integração de E-mail:**
    Abra o arquivo `src/main/resources/application.properties` e defina a propriedade `mail.integracao` com o valor desejado. Os valores esperados para esta POC são `AWS` ou `OCI`.

    Exemplo:
    ```properties
    # Define qual integrador de e-mail será utilizado: AWS ou OCI
    mail.integracao=AWS
    ```
    Ou:
    ```properties
    # Define qual integrador de e-mail será utilizado: AWS ou OCI
    mail.integracao=OCI
    ```

3.  **Construa o Projeto:**
    Navegue até o diretório raiz do projeto no seu terminal e execute o comando Maven para limpar e empacotar o projeto:
    ```bash
    mvn clean package
    ```
    Este comando irá compilar o código, executar os testes e gerar o arquivo JAR executável em `target/`.

## Executando a Aplicação

Após construir o projeto, você pode executá-lo a partir do terminal:

```bash
java -jar target/mail-0.0.1-SNAPSHOT.jar