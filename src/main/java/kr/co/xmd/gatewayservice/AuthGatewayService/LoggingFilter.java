package kr.co.xmd.gatewayservice.AuthGatewayService;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Component
@Slf4j 
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

     public LoggingFilter(){
          super(Config.class);
     } 
     
     @Override
     public GatewayFilter apply(Config config) {

          GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
               ServerHttpRequest request = exchange.getRequest();
               ServerHttpResponse response = exchange.getResponse(); 

               //log.info("Loggin Filter baseMessage : {}", config.getBaseMessage());

               if(config.isPreLogger()){
                    //log.info("Logging Filter PRE Filter :  reqeust id -> {}", request.getId());
               }

               return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    if(config.isPostLogger()){
                         log.info("Logging Filter POST Filter : response code -> {}", response.getStatusCode());
                    }
               }));

          },  Ordered.HIGHEST_PRECEDENCE);

          return filter;
     }
     
     @Setter @Getter
     public static class Config {
          private String baseMessage;
          private boolean preLogger;
          private boolean postLogger;

     }

}
