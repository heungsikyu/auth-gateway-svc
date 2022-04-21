FROM appinair/jdk11-maven

WORKDIR /auth-gateway-svc

VOLUME /tmp 

# CMD ["./auth-gateway-svc/gradlew", "clean", "build"]

# ARG JAR_FILE=./auth-gateway-svc/build/libs/*.jar

COPY ./auth-gateway-svc/build/libs/gateway-service-0.0.1-SNAPSHOT.jar authagatewayservice-0.0.1.jar
COPY ./opentelemetry_agent/opentelemetry-javaagent.jar  opentelemetry-javaagent.jar


ENTRYPOINT ["java", "-javaagent:opentelemetry-javaagent.jar", "-Dotel.exporter.otlp.endpoint=http://192.168.10.157:4317", "-Dotel.resource.attributes=service.name=auth-gateway-svc", "-jar",  "authagatewayservice-0.0.1.jar" ]


#  OTEL_EXPORTER_OTLP_ENDPOINT="http://127.0.0.1:4317" OTEL_RESOURCE_ATTRIBUTES=service.name=auth-gateway-svc 
#  java -javaagent:opentelemetry-javaagent.jar -jar authagatewayservice-0.0.1.jar