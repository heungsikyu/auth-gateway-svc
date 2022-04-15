package kr.co.xmd.gatewayservice;

public interface Ordered {
     int HIGHEST_PRECEDENCE = -2147483648;
     int LOWEST_PRECEDENCE = 2147483647;

     int getOrder();
}