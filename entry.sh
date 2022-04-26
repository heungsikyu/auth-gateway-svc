#!/bin/sh

  ACTIVE_PROFILE="${PROFILE:-dev}"
  ACTIVE_OTEL_EXPORTER_OTLP_ENDPOINT="${OTEL_EXPORTER_OTLP_ENDPOINT:-127.0.0.1}"
  ACTIVE_GATEWAY_PORT="${GATEWAY_PORT:-9999}"

  echo "ACTIVE_PROFILE=${ACTIVE_PROFILE}"
  echo "ACTIVE_OTEL_EXPORTER_OTLP_ENDPOINT=${ACTIVE_OTEL_EXPORTER_OTLP_ENDPOINT}"
  echo "ACTIVE_GATEWAY_PORT=${ACTIVE_GATEWAY_PORT}"
  echo "--------------------------------------------------------------------------"
 
  exec java -javaagent:/app/opentelemetry-javaagent.jar \
        -Dotel.exporter.otlp.endpoint=http://${ACTIVE_OTEL_EXPORTER_OTLP_ENDPOINT}:4317 \
        -Dotel.resource.attributes=service.name=auth-gatewayservice \
        -Dspring.profiles.active=${ACTIVE_PROFILE} \
        -jar /app/AuthGatewayService-prod.jar