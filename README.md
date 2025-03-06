# sus_plus: Sistema Abrangente de Gestão de Saúde

## Visão Geral do Projeto

`sus_plus` é uma aplicação baseada em microsserviços projetada para otimizar e aprimorar a gestão de serviços de saúde. Este projeto visa fornecer um sistema robusto, escalável e eficiente para o gerenciamento de pacientes, agendamento de consultas, gestão de prontuários médicos e outras tarefas cruciais relacionadas à saúde. Ele usa uma abordagem modular, com cada microsserviço lidando com um domínio específico da aplicação.

## Tecnologias Utilizadas

Este projeto utiliza uma variedade de tecnologias modernas para garantir desempenho, confiabilidade e escalabilidade:

*   **Linguagem de Programação:** Java 17
*   **Framework:** Spring Boot (para desenvolvimento de microsserviços, APIs REST e injeção de dependência)
*   **Banco de Dados:** PostgreSQL (para armazenamento persistente de dados)
*   **Broker de Mensagens:** RabbitMQ (para comunicação assíncrona entre microsserviços)
*   **Ferramenta de Build:** Maven (para construção do projeto, gerenciamento de dependências e empacotamento)
*   **Containerização:** Docker (para empacotar e executar microsserviços em contêineres)
*   **Testes:** Junit 5 e Mockito (para testes unitários)

## Microsserviços

O projeto `sus_plus` é composto por vários microsserviços, cada um com suas responsabilidades específicas:

1.  **sus-integrated:**
    *   **Propósito:** Central de gerenciamento de pacientes e hub de integração de dados. Valida os dados do paciente com os outros microsserviços e lida com a criação e gerenciamento de pacientes.
    *   **Banco de Dados:** PostgreSQL (`sus_integrated_database`)
    *   **Endpoints:**
        *   `POST /patient/create`: Cadastra um novo paciente.
            *   **Corpo da Requisição:** `PatientFormDTO` (name, document, phone).
            *   **Resposta:** `PatientDTO` (detalhes do paciente).
        *   `GET /patient/search/{id}`: Recupera um paciente pelo seu ID.
            *   **Resposta:** `PatientDTO` (detalhes do paciente).
        *   `GET /patient/list`: Lista todos os pacientes (paginado).
            *   **Parâmetros:** `page`, `size`, `sort`.
            *   **Resposta:** `Page<PatientDTO>` (lista de pacientes).
        *   `PUT /patient/update/{id}`: Atualiza um paciente existente.
            *   **Corpo da Requisição:** `PatientFormDTO` (name, document, phone).
            *   **Resposta:** `PatientDTO` (detalhes do paciente atualizado).
    * **Dependências:** RabbitMQ
    * **Responsabilidade:** validar dados de pacientes para outros microsserviços, criar, buscar, listar e atualizar pacientes.

2.  **sus-manager:**
    *   **Propósito:** Gerencia unidades de saúde, profissionais e especialidades.
    *   **Banco de Dados:** PostgreSQL (`sus_manager_database`)
    *   **Endpoints:**
        *   *(Endpoints não presentes no código fornecido)*

3.  **sus-unity:**
    *   **Propósito:** Fornece recursos para as unidades de saúde específicas.
    *   **Banco de Dados:** PostgreSQL (`sus_unity_database`)
    *   **Endpoints:**
        *   *(Endpoints não presentes no código fornecido)*

4.  **sus-scheduling:**
    *   **Propósito:** Lida com o agendamento de consultas e operações relacionadas.
    *   **Banco de Dados:** PostgreSQL (`sus_scheduling_database`)
    *   **Endpoints:**
        *   `POST /scheduling`: cria um novo Agendamento.
            *   **Corpo da Requisição:** `SchedulingFormDTO`
            *   **Resposta:** `SchedulingDTO`
        *   `GET /scheduling/patient/{patientId}`: Lista agendamentos para um paciente.
            *   **Resposta:** `List<SchedulingDTO>` (lista de agendamentos).
        *   `GET /scheduling/professional/{professionalId}`: Lista agendamentos para um profissional.
            *   **Resposta:** `List<SchedulingDTO>` (lista de agendamentos).
        *   `PUT /scheduling/{id}/done`: marca o agendamento como concluído.
            * **Resposta:** `SchedulingDTO` (Agendamento atualizado).
    * **Dependências:** RabbitMQ
    * **Responsabilidade:** criar, listar e atualizar um agendamento
5.  **sus-patient-record:**
    *   **Propósito:** Gerencia prontuários médicos para pacientes.
    *   **Banco de Dados:** PostgreSQL (`sus_patient-record_database`)
    *   **Endpoints:**
        *   `POST /patient-records`: Cria um novo prontuário do paciente.
            *   **Corpo da Requisição:** `PatientRecordFormDTO`.
            *   **Resposta:** `PatientRecordOutDTO`.
        *   `GET /patient-records/{id}`: Recupera um prontuário do paciente pelo ID.
            *   **Resposta:** `PatientRecordOutDTO`.
        *   `GET /patient-records/unity/{unityId}/patient/{patientId}`: Recupera prontuários de um paciente específico em uma unidade específica.
            *   **Resposta:** `List<PatientRecordOutDTO>`
        *   `GET /patient-records/professional/{professionalId}`: Recupera todos os prontuários de um profissional específico.
            *   **Resposta:** `List<PatientRecordOutDTO>`
        *   `PUT /patient-records/{id}`: Inclui observações no prontuário do paciente.
            *   **Corpo da Requisição:** `String observation`.
            *   **Resposta:** `PatientRecordOutDTO`.
    * **Dependências:** RabbitMQ
    * **Responsabilidade:** criar, buscar, listar e editar o prontuário do paciente

## Comunicação Entre Microsserviços

*   **Mensageria Assíncrona:** Microsserviços se comunicam de forma assíncrona usando o RabbitMQ. Isso garante um baixo acoplamento e uma melhor tolerância a falhas.
*   **Comunicação Baseada em Filas:** Cada microsserviço escuta filas específicas para processar mensagens relacionadas ao seu domínio.

## Como Executar o Projeto

1.  **Pré-requisitos:**
    *   Docker e Docker Compose
    *   Java 17+
    *   Maven

2.  **Passos:**
    *   Clone o repositório do projeto: `git clone [url_do_repositorio]`
    *   Navegue até o diretório raiz do projeto.
    *   Execute o comando: `docker-compose -f base-compose.yaml up --build`

3. **Acesso:**
    * Após rodar o comando você poderá acessar os microsserviços.
        * **sus-integrated:** http://localhost:8080/swagger-ui/index.html#/
        * **sus-manager:** http://localhost:8081/swagger-ui/index.html#/
        * **sus-unity:** http://localhost:8082/swagger-ui/index.html#/
        * **sus-scheduling:** http://localhost:8083/swagger-ui/index.html#/
        * **sus-patient-record:** http://localhost:8084/swagger-ui/index.html#/
    * RabbitMQ: http://localhost:15672 (usuário: guest, senha: guest)
    * Portas do banco de dados:
        * **sus-integrated-db:** 5432
        * **sus-manager-db:** 5433
        * **sus-unity-db:** 5434
        * **sus-scheduling-db:** 5435
        * **sus-patient-record-db:** 5436

## Como Testar o Projeto

1.  **Testes Unitários:**
    *   Cada microsserviço contém testes unitários que podem ser executados usando o Maven.
    *   Navegue até o diretório do microsserviço (ex: `sus_scheduling`).
    *   Execute o comando: `mvn test`
    * Os testes estão na pasta src/test/java, você pode verificar isso para cada microsserviço.

2.  **Testes de API:**
    *   Use ferramentas como Postman ou `curl` para interagir com as APIs REST dos microsserviços.
    *   Consulte a seção "Endpoints" acima para detalhes sobre a API de cada microsserviço.
    * Você pode executar o comando docker compose e depois enviar a requisição para cada serviço na porta especificada.

3. **Testando o fluxo do sistema**
    * Primeiro você precisará criar um paciente no serviço `sus-integrated` usando o enpoint `POST /patient/create`
    * Depois disso, você pode criar um registro de paciente no `sus-patient-record` usando o endpoint `POST /patient-records`
    * Depois disso, você pode criar um novo agendamento no serviço `sus-scheduling` usando o endpoint `POST /scheduling`  