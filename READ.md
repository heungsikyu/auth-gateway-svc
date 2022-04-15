


OTEL_EXPORTER_OTLP_ENDPOINT="http://127.0.0.1:4317" OTEL_RESOURCE_ATTRIBUTES=service.name=auth-gateway-svc java -javaagent:/Users/heungsikyu/xmd/xmd-auth/opentelemetry_agent/opentelemetry-javaagent.jar -jar /Users/heungsikyu/xmd/xmd-auth/auth-gateway-svc/build/libs/gateway-service-0.0.1-SNAPSHOT.jar