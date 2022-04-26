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
