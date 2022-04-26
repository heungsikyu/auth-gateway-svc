

# XMD Auth Gateway Service (SCG)

## 로컬 호스트에서 서비스 시작 vs 도커 컨테이너로 서비스 시작 

### 1. 로컬 호스트에서 서비스 시작

#### 1.1 컴파일 
gradle build 방식과 컴파일 빌드
__기존 소스가 있다면 삭제 한다.__ 
 ```bash
    ./gradlew clean 
 ```

  application.yaml 파일에서 Eureka server에 등록 하여야 하기때문에 아래 처럼 defalutZone의 아이피를 환경에 맞게 수정한다. 
```yaml
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://192.168.219.102:8761/eureka
```
  __gradle을 통한 컴파일 및 jar 파일 생성__ 
 
  ```bash
    ./gradlew build 
 ```


#### 1.2 서비스 시작

```bash
OTEL_EXPORTER_OTLP_ENDPOINT="http://192.168.219.102:4317" OTEL_RESOURCE_ATTRIBUTES=service.name=auth-gateway-svc \
java -javaagent:/Users/heungsikyu/xmd/xmd-auth/opentelemetry_agent/opentelemetry-javaagent.jar \
-jar /Users/heungsikyu/xmd/xmd-auth/auth-gateway-svc/build/libs/AuthGatewayService-prod.jar
```

### 2. 도커 컨테이너로 서비스 시작 

#### 2.1 필요파일 생성 
- __도커 파일 생성 :  Dockerfile__

```yaml
# 실행 파일 컴파일 준비 
FROM openjdk:11 AS builder

# 변수 사용 
ENV APP_HOME=/app

# 작업 폴더 위치 설정 
WORKDIR $APP_HOME

VOLUME /tmp 

COPY ./opentelemetry_agent/*.jar  $APP_HOME/opentelemetry-javaagent.jar

COPY gradlew $APP_HOME
COPY build.gradle $APP_HOME
COPY settings.gradle $APP_HOME
COPY gradle $APP_HOME/gradle
COPY entry.sh $APP_HOME/run.sh  

RUN chmod +x $APP_HOME/gradlew

COPY src src

# 컴파일 빌드 
RUN ./gradlew build --stacktrace


# Docker 이미지 생성 
FROM openjdk:11  
ENV APP_HOME=/app

# container 내 작업폴더 
WORKDIR $APP_HOME

#위 실행 파일 builder에서 빌드한 AuthGatewayService-prod.jar, run.sh, opentelemetry-javaagent.jar를 /app 위치에 복사
COPY --from=builder $APP_HOME/build/libs/AuthGatewayService-prod.jar  . 
COPY --from=builder $APP_HOME/run.sh .
COPY --from=builder $APP_HOME/opentelemetry-javaagent.jar .

# 실행 권한 주기 
RUN chmod 774 $APP_HOME/run.sh 

ENV PROFILE='local'
ENV OTEL_EXPORTER_OTLP_ENDPOINT = '127.0.0.1'
ENV API_PORT='9999'

EXPOSE ${API_PORT}

ENTRYPOINT ["/app/run.sh"]
```

- __컨테이너 안에서 실행할 shell 프로그램 생성 : entry.sh__

```bash 
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
          
```


#### 2.2 docker 이미지 만들기  

```bash
docker build -t authgatewayservice:0.1 .    
```


#### 2.2 docker 이미지로 auth-gateway-service 컨테이너 시작

```bash
docker run --name auth-gateway-service -p 9998:9998 -e "PROFILE=dev" -e "OTEL_EXPORTER_OTLP_ENDPOINT=192.168.10.157" -e "GATEWAY_PORT=9998" authgatewayservice:0.1  
```


#### [참고 링크] 
 도커 파일
* [SpringBoot Application Dockerfile 활용](https://spring.io/guides/topicals/spring-boot-docker)