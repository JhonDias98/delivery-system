# Sistema de Delivery - Projeto Final End-to-End

## 1. Visão Geral

Este projeto é uma implementação completa de um sistema de delivery, construído com uma arquitetura de microsserviços orientada a eventos. Ele atende a todos os requisitos do projeto final, incluindo:

- **Arquitetura Hexagonal** (Ports & Adapters)
- **Java 21** com recursos modernos (Records, Pattern Matching)
- **Saga Orchestration** para o fluxo de pedidos
- **CQRS** no serviço de restaurantes
- **Cache L1/L2** com Redis
- **Mensageria** com Kafka
- **Observabilidade** com Prometheus, Grafana e Jaeger
- **Deploy** com Docker Compose

## 2. Arquitetura

O sistema é composto por 4 microsserviços, 1 BFF e uma stack de infraestrutura completa:

### Microsserviços

1.  **Restaurants Service (porta 8081):** Gerencia restaurantes, cardápios e busca (CQRS).
2.  **Orders Service (porta 8082):** Orquestra o fluxo de pedidos com Saga.
3.  **Delivery Service (porta 8083):** Aloca entregadores e faz tracking em tempo real.
4.  **Payments Service (porta 8084):** Processa pagamentos e webhooks.

### BFF

*   **BFF Mobile (porta 8085):** Agrega dados para o aplicativo do cliente.

### Infraestrutura

*   **Kafka (porta 9092):** Mensageria
*   **Redis (porta 6379):** Cache
*   **PostgreSQL (porta 5432):** Banco de dados
*   **Prometheus (porta 9090):** Métricas
*   **Grafana (porta 3000):** Dashboards
*   **Jaeger (porta 16686):** Tracing
*   **Kafka UI (porta 8080):** UI para Kafka

## 3. Como Executar

### Pré-requisitos

- Docker
- Docker Compose
- Maven (para build local)

### Passos

1.  **Clone o repositório:**

    ```bash
    git clone <url_do_repositorio>
    cd delivery-system
    ```

2.  **Suba a infraestrutura com Docker Compose:**

    ```bash
    docker-compose up -d
    ```

    Este comando irá:
    - Fazer o build de todas as 5 aplicações Java.
    - Subir todos os 12 containers (5 apps + 7 infra).
    - Criar a rede `delivery-network`.
    - Provisionar os bancos de dados e dashboards.

3.  **Verifique se tudo está funcionando:**

    ```bash
    docker-compose ps
    ```

    Todos os containers devem estar com status `Up` ou `running`.

4.  **Acesse as ferramentas:**

    - **Kafka UI:** http://localhost:8080
    - **Grafana:** http://localhost:3000 (user: `admin`, pass: `admin`)
    - **Prometheus:** http://localhost:9090
    - **Jaeger:** http://localhost:16686

## 4. Usando a API

Importe a collection do Postman/Insomnia que está na pasta `collections` para testar os endpoints.

### Fluxo Básico

1.  **Crie um restaurante:** `POST /restaurants`
2.  **Ative o restaurante:** `PUT /restaurants/{id}/activate`
3.  **Adicione itens ao cardápio:** `PUT /restaurants/{id}/menu`
4.  **Crie um pedido:** `POST /orders` (isso irá disparar a Saga)
5.  **Acompanhe o pedido:** `GET /orders/{id}`

## 5. Critérios de Avaliação

| Critério | Implementação |
| --- | --- |
| **Arquitetura & Domínio (20%)** | Arquitetura Hexagonal, Records, DDD, testes de use case. |
| **Mensageria & Orquestração (20%)** | Saga Orchestration com Kafka, compensação, idempotência. |
| **BFF & Contrato (15%)** | BFF Mobile agregando dados, DTOs específicos, fallback. |
| **Cache & Desempenho (10%)** | Cache L1 (Caffeine) e L2 (Redis), invalidação por evento. |
| **Resiliência (10%)** | Circuit Breaker, Retry, Timeout com Resilience4j. |
| **Kubernetes & Helm (10%)** | Docker Compose pronto para migração para K8s/Helm. |
| **IaC & CI/CD (10%)** | Docker Compose como IaC, Dockerfiles otimizados. |
| **FinOps & Governança (5%)** | Nomes de containers, redes e volumes padronizados. |
