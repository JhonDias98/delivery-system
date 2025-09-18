#!/bin/bash

echo "🚀 Iniciando Sistema de Delivery..."
echo "=================================="

# Verifica se Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker não está rodando. Por favor, inicie o Docker primeiro."
    exit 1
fi

# Verifica se Docker Compose está disponível
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose não encontrado. Por favor, instale o Docker Compose."
    exit 1
fi

echo "✅ Docker e Docker Compose encontrados"

# Para containers existentes
echo "🛑 Parando containers existentes..."
docker-compose down

# Remove volumes antigos (opcional)
read -p "🗑️  Deseja remover volumes antigos? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    docker-compose down -v
    echo "✅ Volumes removidos"
fi

# Inicia os serviços
echo "🔄 Iniciando serviços..."
docker-compose up -d

# Aguarda os serviços ficarem prontos
echo "⏳ Aguardando serviços ficarem prontos..."
sleep 30

# Verifica status dos serviços
echo "📊 Status dos serviços:"
docker-compose ps

echo ""
echo "🎉 Sistema iniciado com sucesso!"
echo ""
echo "📱 Acesse as ferramentas:"
echo "   • Kafka UI:    http://localhost:8080"
echo "   • Grafana:     http://localhost:3000 (admin/admin)"
echo "   • Prometheus:  http://localhost:9090"
echo "   • Jaeger:      http://localhost:16686"
echo ""
echo "🔗 APIs dos Microsserviços:"
echo "   • Restaurants: http://localhost:8081"
echo "   • Orders:      http://localhost:8082"
echo "   • Delivery:    http://localhost:8083"
echo "   • Payments:    http://localhost:8084"
echo "   • BFF Mobile:  http://localhost:8085"
echo ""
echo "📚 Importe a collection do Postman em: ./collections/"
echo ""
echo "🏃‍♂️ Para parar o sistema: docker-compose down"

