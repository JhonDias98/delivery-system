#!/bin/bash

# Script para criar tópicos Kafka necessários para o sistema de delivery
# Este script deve ser executado após o Kafka estar rodando

KAFKA_CONTAINER="delivery-kafka"
KAFKA_BROKER="kafka:29092"

echo "🚀 Criando tópicos Kafka para o sistema de delivery..."

# Função para criar tópico
create_topic() {
    local topic_name=$1
    local partitions=${2:-3}
    local replication_factor=${3:-1}

    echo "📝 Criando tópico: $topic_name"
    docker exec $KAFKA_CONTAINER kafka-topics --create \
        --bootstrap-server $KAFKA_BROKER \
        --topic $topic_name \
        --partitions $partitions \
        --replication-factor $replication_factor \
        --if-not-exists
}

# === TÓPICOS DE EVENTOS DE DOMÍNIO ===

# Restaurant Events
create_topic "restaurant-created-events" 3 1
create_topic "restaurant-activated-events" 3 1
create_topic "restaurant-deactivated-events" 3 1
create_topic "menu-updated-events" 3 1
create_topic "restaurant-rating-updated-events" 3 1

# Order Events
create_topic "order-created-events" 3 1
create_topic "order-confirmed-events" 3 1
create_topic "order-cancelled-events" 3 1
create_topic "order-completed-events" 3 1

# Delivery Events
create_topic "delivery-allocated-events" 3 1
create_topic "delivery-started-events" 3 1
create_topic "delivery-completed-events" 3 1
create_topic "delivery-cancelled-events" 3 1
create_topic "delivery-person-location-updated-events" 3 1

# Payment Events
create_topic "payment-created-events" 3 1
create_topic "payment-approved-events" 3 1
create_topic "payment-failed-events" 3 1
create_topic "payment-refunded-events" 3 1

# === TÓPICOS DE SAGA ===

# Restaurant Saga
create_topic "restaurant-validation-requested-events" 3 1
create_topic "restaurant-validation-successful-events" 3 1
create_topic "restaurant-validation-failed-events" 3 1
create_topic "restaurant-compensation-requested-events" 3 1

# Delivery Saga
create_topic "delivery-allocation-requested-events" 3 1
create_topic "delivery-allocation-successful-events" 3 1
create_topic "delivery-allocation-failed-events" 3 1
create_topic "delivery-compensation-requested-events" 3 1

# Payment Saga
create_topic "payment-processing-requested-events" 3 1
create_topic "payment-processing-successful-events" 3 1
create_topic "payment-processing-failed-events" 3 1
create_topic "payment-compensation-requested-events" 3 1

# === TÓPICOS DE INTEGRAÇÃO ===

# Dead Letter Queue
create_topic "delivery-system-dlq" 3 1

# Audit Events
create_topic "audit-events" 3 1

# Notification Events
create_topic "notification-events" 3 1

echo "✅ Todos os tópicos Kafka foram criados com sucesso!"

# Listar tópicos criados
echo "📋 Listando tópicos criados:"
docker exec $KAFKA_CONTAINER kafka-topics --list --bootstrap-server $KAFKA_BROKER

echo "🎉 Configuração do Kafka concluída!"