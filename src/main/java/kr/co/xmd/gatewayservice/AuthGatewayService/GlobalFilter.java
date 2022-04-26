package kr.co.xmd.gatewayservice.AuthGatewayService;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
     public GlobalFilter(){
          super(Config.class);
     }
     
     @Override
     public GatewayFilter apply(Config config) {
          return ((exchange, chain) -> {
               ServerHttpRequest request = exchange.getRequest();
               ServerHttpResponse reponse = exchange.getResponse();

               //log.info("Global Filter baseMessage : {}", config.getBaseMessage());

               if(config.isPreLogger()){
                    //log.info("Global Filter start : request id -> {}", request.getId());
               }

               return chain.filter(exchange).then(Mono.fromRunnable(() ->{
                    if(config.isPostLogger()){ 
                         log.info("Global Filter End  : response code -> {}", reponse.getStatusCode());
                    }
               }));
          });
     }



     @Setter @Getter
     public static class Config{
          String baseMessage;
          private boolean preLogger;
          private boolean postLogger;

     }
     
}
