# Configuração dos Tópicos Kafka

Esta pasta contém as configurações e scripts necessários para configurar os tópicos Kafka utilizados pelo sistema de delivery com arquitetura hexagonal.

## 📁 Arquivos

### `create-topics.sh`
Script bash para criar automaticamente todos os tópicos Kafka necessários para o sistema.

**Como usar:**
```bash
# Certifique-se de que o Kafka está rodando
docker-compose up -d kafka

# Execute o script
./infrastructure/kafka-topics/create-topics.sh
```

### `topics-config.yml`
Arquivo de configuração que documenta todos os tópicos, suas configurações e consumer groups.

## 🎯 Tópicos Criados

### Eventos de Domínio

#### Restaurant Service
- `restaurant-created-events` - Eventos de criação de restaurante
- `restaurant-activated-events` - Eventos de ativação de restaurante
- `restaurant-deactivated-events` - Eventos de desativação de restaurante
- `menu-updated-events` - Eventos de atualização de menu
- `restaurant-rating-updated-events` - Eventos de atualização de avaliação

#### Order Service
- `order-created-events` - Eventos de criação de pedido
- `order-confirmed-events` - Eventos de confirmação de pedido
- `order-cancelled-events` - Eventos de cancelamento de pedido
- `order-completed-events` - Eventos de conclusão de pedido

#### Delivery Service
- `delivery-allocated-events` - Eventos de alocação de entrega
- `delivery-started-events` - Eventos de início de entrega
- `delivery-completed-events` - Eventos de conclusão de entrega
- `delivery-cancelled-events` - Eventos de cancelamento de entrega
- `delivery-person-location-updated-events` - Eventos de atualização de localização

#### Payment Service
- `payment-created-events` - Eventos de criação de pagamento
- `payment-approved-events` - Eventos de aprovação de pagamento
- `payment-failed-events` - Eventos de falha de pagamento
- `payment-refunded-events` - Eventos de estorno de pagamento

### Eventos de Saga

#### Restaurant Saga
- `restaurant-validation-requested-events`
- `restaurant-validation-successful-events`
- `restaurant-validation-failed-events`
- `restaurant-compensation-requested-events`

#### Delivery Saga
- `delivery-allocation-requested-events`
- `delivery-allocation-successful-events`
- `delivery-allocation-failed-events`
- `delivery-compensation-requested-events`

#### Payment Saga
- `payment-processing-requested-events`
- `payment-processing-successful-events`
- `payment-processing-failed-events`
- `payment-compensation-requested-events`

### Tópicos de Integração
- `delivery-system-dlq` - Dead Letter Queue
- `audit-events` - Eventos de auditoria
- `notification-events` - Eventos de notificação

## 🔧 Configurações Padrão

- **Partições**: 3 (padrão)
- **Fator de Replicação**: 1 (desenvolvimento)
- **Retenção**: Varia por tipo de evento (1 dia a 90 dias)
- **Compressão**: Snappy
- **Política de Limpeza**: Delete

## 🚀 Como Executar

1. **Iniciar a infraestrutura:**
   ```bash
   docker-compose up -d zookeeper kafka
   ```

2. **Aguardar o Kafka estar pronto:**
   ```bash
   # Verificar se o Kafka está rodando
   docker logs delivery-kafka
   ```

3. **Criar os tópicos:**
   ```bash
   ./infrastructure/kafka-topics/create-topics.sh
   ```

4. **Verificar tópicos criados:**
   ```bash
   docker exec delivery-kafka kafka-topics --list --bootstrap-server kafka:29092
   ```

## 🔍 Monitoramento

Você pode monitorar os tópicos através do Kafka UI disponível em:
- **URL**: http://localhost:8080
- **Cluster**: delivery-cluster
