#!/bin/bash

# =============================================================
#  reset-topics.sh — Limpa os 4 tópicos Kafka do sistema bank
#  Deleta e recria cada tópico com 30 partições / RF=1
# =============================================================

KAFKA_CONTAINER="infra-kafka-1"
BOOTSTRAP="kafka:29092"
PARTITIONS=30
REPLICATION=1

TOPICS=(
  "kafka-topic-encargos-nao-iniciados"
  "kafka-topic-encargos-nao-iniciados-dlt"
  "kafka-topic-status-conta-request"
  "kafka-topic-status-conta-request-dlt"
  "kafka-topic-status-conta-response"
  "kafka-topic-status-conta-response-dlt"
  "kafka-topic-encargos-processados"
  "kafka-topic-encargos-processados-dlt"
  
)

echo "============================================="
echo " Iniciando reset dos tópicos Kafka"
echo " Container : $KAFKA_CONTAINER"
echo " Bootstrap : $BOOTSTRAP"
echo "============================================="
echo ""

# ── 1. Deletar todos os tópicos ──────────────────────────────
echo ">>> Deletando tópicos..."
for TOPIC in "${TOPICS[@]}"; do
  echo "  - Deletando: $TOPIC"
  docker exec "$KAFKA_CONTAINER" \
    kafka-topics --bootstrap-server "$BOOTSTRAP" \
    --delete --topic "$TOPIC" 2>&1 | grep -v "^$"
done

echo ""
echo ">>> Aguardando confirmação das deleções (5s)..."
sleep 5

# ── 2. Recriar todos os tópicos ──────────────────────────────
echo ""
echo ">>> Recriando tópicos..."
for TOPIC in "${TOPICS[@]}"; do
  echo "  - Criando: $TOPIC (partitions=$PARTITIONS, replication-factor=$REPLICATION)"
  docker exec "$KAFKA_CONTAINER" \
    kafka-topics --bootstrap-server "$BOOTSTRAP" \
    --create --topic "$TOPIC" \
    --partitions "$PARTITIONS" \
    --replication-factor "$REPLICATION" \
    --if-not-exists 2>&1 | grep -v "^$"
done

echo ""

# ── 3. Verificar resultado ───────────────────────────────────
echo ">>> Verificando tópicos criados:"
docker exec "$KAFKA_CONTAINER" \
  kafka-topics --bootstrap-server "$BOOTSTRAP" \
  --describe \
  --topics-with-overrides 2>/dev/null | grep -E "Topic:|PartitionCount" | \
  grep -A1 "kafka-topic-"

echo ""
echo "============================================="
echo " Reset concluído!"
echo "============================================="
