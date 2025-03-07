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

         **Gestão de Profissionais de Saúde**
         *   **`GET /manager/professional/findall`**
             *   **Descrição:** Recupera todos os profissionais.
             *   **Resposta de Sucesso (200 OK):** `List<ProfessionalManagerOut>` (uma lista de todos os profissionais).
         *   **`GET /manager/professional/find/{professionalId}`**
             *   **Descrição:** Recupera um profissional pelo ID.
             *   **Parâmetros:**
                 *   `professionalId` (UUID): ID do profissional.
             *   **Resposta de Sucesso (200 OK):** `ProfessionalManagerOut` (o profissional correspondente ao ID fornecido).
         *   **`POST /manager/professional/create`**
             *   **Descrição:** Cria um novo profissional.
             *   **Corpo da Requisição:**
                 ```json
                     {
                         "name": "João silva",
                         "document": "12345678900",
                         "phone": "11999999999",
                         "especialityId": "uuid-da-especialidade",
                         "unityId": "uuid-da-unidade",
                         "active": true
                     }
                 ```
             *   **Resposta de Sucesso (201 CREATED):** `ProfessionalManagerOut` (o profissional criado).
         *   **`PUT /manager/professional/alter/{professionalId}`**
             *   **Descrição:** Atualiza um profissional existente.
             *   **Parâmetros:**
                 *   `professionalId` (UUID): ID do profissional a ser atualizado.
             *   **Corpo da Requisição:** (Dados para atualização)
             *   **Resposta de Sucesso (200 OK):** Retorna a unidade atualizada.
         * **`PUT /manager/professional/include-speciality/{professionalId}/{idSpeciality}`**
             * **Descrição:** adiciona uma especialidade a um profissional
             * **Parametros:**
                 *   `professionalId` (UUID): ID do profissional.
                 * `idSpeciality` (UUID): ID da especialidade.
             * **Resposta de Sucesso (200 OK):** `OK`
         * **`PUT /manager/professional/exclude-speciality/{professionalId}/{idSpeciality}`**
             * **Descrição:** remove uma especialidade de um profissional
             * **Parametros:**
                 *   `professionalId` (UUID): ID do profissional.
                 * `idSpeciality` (UUID): ID da especialidade.
             * **Resposta de Sucesso (200 OK):** `OK`
         * **`DELETE /manager/professional/delete/{professionalId}`**
             * **Descrição:** remove o profissional
             * **Parametros:**
                 *   `professionalId` (UUID): ID do profissional.
             * **Resposta de Sucesso (200 OK):** `OK`

         **Gestão de Especialidades**
         *   **`POST /speciality`**
             *   **Descrição:** Cadastra uma nova especialidade.
             *   **Corpo da Requisição:**
                 ```json
                 {
                   "name": "cardiologia",
                   "description": "especialidade do coração"
                 }
                 ```
             *   **Resposta de Sucesso (200 OK):** Retorna a especialidade criada.
         *   **`GET /speciality/find/{id}`**
             *   **Descrição:** Busca uma especialidade específica.
             *   **Parâmetros:**
                 *   `id` (UUID): ID da especialidade.
             *   **Resposta de Sucesso (200 OK):** Retorna a especialidade encontrada.
         *   **`GET /speciality/all`**
             *   **Descrição:** Lista todas as especialidades.
             *   **Resposta de Sucesso (200 OK):** Retorna uma lista de especialidades.

3.  **sus-unity:**
    *   **Propósito:** Fornece recursos para as unidades de saúde específicas.
    *   **Banco de Dados:** PostgreSQL (`sus_unity_database`)
*   **`POST /unity/create`**
    *   **Descrição:** Cria uma nova unidade de saúde.
    *   **Corpo da Requisição:**
        ```json
        {
          "name": "Hospital Central",
          "address": "Rua Principal, 123",
          "phone": "11987654321",
          "quantity": 10
        }
        ```
    *   **Resposta de Sucesso (200 OK):** Retorna a unidade criada.
    *   **Resposta Erro:** Pode retornar `400 BAD REQUEST` caso os dados enviados estejam inconsistentes ou `500 INTERNAL SERVER ERROR` para erros internos.

*   **`GET /unity/find/all`**
    *   **Descrição:** Lista todas as unidades de saúde cadastradas.
    *   **Resposta de Sucesso (200 OK):** Retorna uma lista de unidades.
    *   **Resposta Erro:** Pode retornar `500 INTERNAL SERVER ERROR` para erros internos.

*   **`GET /unity/find/{id}`**
    *   **Descrição:** Busca uma unidade de saúde pelo ID.
    *   **Parâmetros:**
        *   `id` (UUID): ID da unidade de saúde.
    *   **Resposta de Sucesso (200 OK):** Retorna a unidade encontrada.
    *   **Resposta Erro:** Pode retornar `404 NOT FOUND` caso a unidade não exista ou `500 INTERNAL SERVER ERROR` para erros internos.

*   **`POST /unity/update/{id}`**
    *   **Descrição:** Atualiza os dados de uma unidade de saúde.
    *   **Parâmetros:**
        *   `id` (UUID): ID da unidade de saúde a ser atualizada.
    *   **Corpo da Requisição:**
    ```json
        {
          "name": "Novo nome",
          "address": "Nova rua",
          "phone": "11911112222",
          "quantity": 15
        }
    ```
    *   **Resposta de Sucesso (200 OK):** Retorna a unidade atualizada.
    *   **Resposta Erro:** Pode retornar `400 BAD REQUEST` caso os dados enviados estejam inconsistentes, `404 NOT FOUND` caso a unidade não exista ou `500 INTERNAL SERVER ERROR` para erros internos.

*   **`DELETE /unity/{id}`**
    *   **Descrição:** Remove uma unidade de saúde.
    *   **Parâmetros:**
        *   `id` (UUID): ID da unidade de saúde a ser removida.
    *   **Resposta de Sucesso (200 OK):** `"unidade Deletada"`
    *   **Resposta Erro:** Pode retornar `404 NOT FOUND` caso a unidade não exista ou `500 INTERNAL SERVER ERROR` para erros internos.

*   **`PUT /unity/update/{id}/{quantity}/in`**
    *   **Descrição:** Adiciona a quantidade de atendimento na unidade.
    *   **Parâmetros:**
        *   `id` (UUID): ID da unidade de saúde.
        *   `quantity` (int): quantidade a ser adicionada.
    *   **Resposta de Sucesso (200 OK):** Retorna a unidade atualizada.
    *   **Resposta Erro:** Pode retornar `400 BAD REQUEST` caso os dados enviados estejam inconsistentes, `404 NOT FOUND` caso a unidade não exista ou `500 INTERNAL SERVER ERROR` para erros internos.

*   **`PUT /unity/update/{id}/{quantity}/out`**
    *   **Descrição:** Retira a quantidade de atendimento na unidade.
    *   **Parâmetros:**
        *   `id` (UUID): ID da unidade de saúde.
        *   `quantity` (int): quantidade a ser retirada.
    *   **Resposta de Sucesso (200 OK):** Retorna a unidade atualizada.
    *   **Resposta Erro:** Pode retornar `400 BAD REQUEST` caso os dados enviados estejam inconsistentes, `404 NOT FOUND` caso a unidade não exista ou `500 INTERNAL SERVER ERROR` para erros internos.

*   **`POST /unity/include/professional`**
    *   **Descrição:** Inclui um profissional em uma unidade.
    *   **Corpo da Requisição:**
        ```json
        {
          "unityId": "uuid-da-unidade",
          "professionalId": "uuid-do-profissional"
        }
        ```
    *   **Resposta de Sucesso (200 OK):** `OK`
    *   **Resposta Erro:** Pode retornar `400 BAD REQUEST` caso os dados enviados estejam inconsistentes, `404 NOT FOUND` caso a unidade ou o profissional não exista ou `500 INTERNAL SERVER ERROR` para erros internos.

*   **`POST /unity/exclude/professional`**
    *   **Descrição:** Retira um profissional de uma unidade.
    *   **Corpo da Requisição:**
        ```json
        {
          "unityId": "uuid-da-unidade",
          "professionalId": "uuid-do-profissional"
        }
        ```
    *   **Resposta de Sucesso (200 OK):** `OK`
    *   **Resposta Erro:** Pode retornar `400 BAD REQUEST` caso os dados enviados estejam inconsistentes, `404 NOT FOUND` caso a unidade ou o profissional não exista ou `500 INTERNAL SERVER ERROR` para erros internos.
    *   **`POST /professional-availability/create`**
    *   **Descrição:** Cria um novo registro de disponibilidade para um profissional.
    *   **Corpo da Requisição:**
        ```json
        {
            "professionalId": "uuid-do-profissional",
            "unityId": "uuid-da-unidade",
            "date": "AAAA-MM-DD",
            "startTime": "HH:MM",
            "endTime": "HH:MM"
        }
        ```
        * **Exemplo de como fica a data:** 2024-12-01
        * **Exemplo de como fica o horario:** 10:00
    *   **Resposta de Sucesso (201 CREATED):** Retorna o registro de disponibilidade criado.
    *   **Resposta Erro:** Pode retornar `400 BAD REQUEST` caso os dados enviados estejam inconsistentes ou `500 INTERNAL SERVER ERROR` para erros internos.

*   **`GET /professional-availability/list-all`**
    *   **Descrição:** Lista todos os registros de disponibilidade de profissionais.
    *   **Resposta de Sucesso (200 OK):** Retorna uma lista de registros de disponibilidade.
    *   **Resposta Erro:** Pode retornar `500 INTERNAL SERVER ERROR` para erros internos.

*   **`GET /professional-availability/professional/{professionalId}`**
    *   **Descrição:** Lista os registros de disponibilidade para um profissional específico.
    *   **Parâmetros:**
        *   `professionalId` (UUID): ID do profissional.
    *   **Resposta de Sucesso (200 OK):** Retorna uma lista de registros de disponibilidade para o profissional.
    *   **Resposta Erro:** Pode retornar `500 INTERNAL SERVER ERROR` para erros internos.

*   **`GET /professional-availability/date/{date}`**
    *   **Descrição:** Lista os registros de disponibilidade para uma data específica e unidade.
    *   **Parâmetros:**
        *   `date` (LocalDate): Data no formato `AAAA-MM-DD`.
        * `unityId` (UUID): ID da unidade a ser pesquisada.
    *   **Resposta de Sucesso (200 OK):** Retorna uma lista de registros de disponibilidade para a data e unidade.
    *   **Resposta Erro:** Pode retornar `500 INTERNAL SERVER ERROR` para erros internos.

*   **`PUT /professional-availability/update/{id}`**
    *   **Descrição:** Atualiza um registro de disponibilidade.
    *   **Parâmetros:**
        *   `id` (UUID): ID do registro de disponibilidade a ser atualizado.
    *   **Corpo da Requisição:**
        ```json
        {
            "professionalId": "uuid-do-profissional",
            "unityId": "uuid-da-unidade",
            "date": "AAAA-MM-DD",
            "startTime": "HH:MM",
            "endTime": "HH:MM"
        }
        ```
        * **Exemplo de como fica a data:** 2024-12-01
        * **Exemplo de como fica o horario:** 10:00
    *   **Resposta de Sucesso (200 OK):** Retorna o registro de disponibilidade atualizado.
    *   **Resposta Erro:** Pode retornar `400 BAD REQUEST` caso os dados enviados estejam inconsistentes, `404 NOT FOUND` caso o registro não exista ou `500 INTERNAL SERVER ERROR` para erros internos.
    * **Dependências:** RabbitMQ
    * **Responsabilidade:** validar dados de pacientes para outros microsserviços, criar, buscar, listar e atualizar pacientes.

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
    *   Use ferramentas como Postman ou a própria documentação atravez do Swagger para interagir com as APIs REST dos microsserviços.
    *   Consulte a seção "Endpoints" acima para detalhes sobre a API de cada microsserviço.
    * Você pode executar o comando docker compose e depois enviar a requisição para cada serviço na porta especificada.

3. **Testando o fluxo do sistema**
    * Primeiro você precisará criar um paciente no serviço `sus-integrated` usando o enpoint `POST /patient/create`
    * Depois disso, você pode criar um médico no serviço  `sus-manager` usando o endpoint `POST /manager/professional/create`
    * Depois disso, você pode criar uma especialidade no serviço  `sus-manager` usando o endpoint `POST /speciality`
    * Depois disso, você associa o medico a especialidade no serviço  `sus-manager` usando o endpoint `PUT /manager/professional/include-speciality/{professionalId}/{idSpeciality}`
    * Depois disso, cria uma unidade no serviço  `sus-unity` usando o endpoint `POST /unity/create`
    * Depois disso, você associa o medico a unidade no serviço  `sus-unity` usando o endpoint `PUT /unity/include/professional`
    * Depois disso, você pode criar um horario disponivel no serviço `sus-unity` usando o endpoint `POST /professional-availability/create`
    * Depois disso, você pode criar um novo agendamento no serviço `sus-scheduling` usando o endpoint `POST /scheduling`
    * Depois disso, você pode criar um registro de paciente no `sus-patient-record` usando o endpoint `POST /patient-records`
    * Vale lembrar que a criação de profissional, especialidade e unidade não necessariamente precisam ter os ids integrados, pode-se criar e depois associar
   
