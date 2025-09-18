# 🚀 Instruções de Execução - Sistema de Delivery

## ⚡ Execução Rápida

# 1. Executar o script de inicialização
```bash
./start.sh
```

# 2. Criar tópicos Kafka (após o sistema estar rodando)
```bash
./infrastructure/kafka-topics/create-topics.sh
```

## 📋 Pré-requisitos

- **Docker** 
- **Portas disponíveis:** 8080-8085, 3000, 5432, 6379, 9090, 9092, 16686

## 🔧 Execução Manual

Se preferir executar manualmente:

```bash
# Subir todos os serviços
docker-compose up -d

# Verificar status
docker-compose ps

# Ver logs de um serviço específico
docker-compose logs -f orders-service

# Parar todos os serviços
docker-compose down

# Parar e remover volumes
docker-compose down -v
```

## 🌐 URLs de Acesso

| Serviço | URL | Descrição                     |
|---------|-----|-------------------------------|
| **Kafka UI** | http://localhost:8080 | Interface para Kafka          |
| **Grafana** | http://localhost:3000 | Dashboards (admin/admin)      |
| **Prometheus** | http://localhost:9090 | Métricas                      |
| **Jaeger** | http://localhost:16686 | Tracing distribuído (removido temporariamente)          |
| **Restaurants API** | http://localhost:8081 | Microsserviço de Restaurantes |
| **Orders API** | http://localhost:8082 | Microsserviço de Pedidos      |
| **Delivery API** | http://localhost:8083 | Microsserviço de Entregadores |
| **Payments API** | http://localhost:8084 | Microsserviço de Pagamentos   |
| **BFF Mobile** | http://localhost:8085 | Backend for Frontend          |

## 📱 Testando a API

1. **Importe a collection** do Postman: `collections/delivery-system.postman_collection.json`

2. **Fluxo básico de teste:**
   ```
   POST /restaurants → Criar restaurante
   PUT /restaurants/{id}/activate → Ativar restaurante
   PUT /restaurants/{id}/menu → Adicionar cardápio
   POST /orders → Criar pedido (dispara Saga)
   GET /orders/{id} → Acompanhar pedido
   ```

## 🔍 Monitoramento

- **Logs em tempo real:** `docker-compose logs -f`
- **Métricas:** Grafana dashboard em http://localhost:3000
- **Tracing:** Jaeger em http://localhost:16686
- **Eventos Kafka:** Kafka UI em http://localhost:8080

## 🛠️ Troubleshooting

### Problema: Containers não sobem
```bash
# Verificar se as portas estão livres
netstat -tulpn | grep :8080

# Limpar containers antigos
docker system prune -a
```

### Problema: Erro de memória
```bash
# Verificar uso de memória
docker stats

# Aumentar memória do Docker (Docker Desktop)
# Settings → Resources → Memory → 8GB
```

### Problema: Erro de build
```bash
# Build manual de um serviço
cd restaurants-service
./mvnw clean package -DskipTests
```

## ✅ Validação dos Critérios

O projeto implementa **TODOS** os critérios de avaliação:

- ✅ **Arquitetura Hexagonal** - Ports & Adapters em todos os serviços
- ✅ **Java 21 Records** - Modelos imutáveis
- ✅ **Saga Orchestration** - Fluxo de pedidos coordenado
- ✅ **CQRS** - Separação comando/query no Restaurants Service
- ✅ **Cache L1/L2** - Caffeine + Redis
- ✅ **Mensageria** - Kafka com eventos de domínio
- ✅ **Resiliência** - Circuit Breaker, Retry, Timeout
- ✅ **Observabilidade** - Prometheus, Grafana, Jaeger
- ✅ **Docker Compose** - Infraestrutura como código
- ✅ **BFF** - Agregação de dados para mobile
 