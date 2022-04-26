package kr.co.xmd.gatewayservice.AuthGatewayService;

public interface Ordered {
     int HIGHEST_PRECEDENCE = -2147483648;
     int LOWEST_PRECEDENCE = 2147483647;

     int getOrder();
}