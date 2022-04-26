

# Spring Cloud Gateway Service 

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
-jar /Users/heungsikyu/xmd/xmd-auth/auth-gateway-svc/build/libs/gateway-service-0.0.1-SNAPSHOT.jar
```



#### [참고 링크] 
 도커 파일
* [SpringBoot Application Dockerfile 활용](https://spring.io/guides/topicals/spring-boot-docker)