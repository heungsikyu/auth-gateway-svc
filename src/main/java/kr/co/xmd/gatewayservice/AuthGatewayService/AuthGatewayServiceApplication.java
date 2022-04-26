package kr.co.xmd.gatewayservice.AuthGatewayService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AuthGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthGatewayServiceApplication.class, args);
		// new SpringApplicationBuilder(GatewayServiceApplication.class)
		// .properties("spring.config.location=classpath:application.yml")
		// .run(args);
	}


	// @Autowired
	// private DiscoveryClient discoveryClient;

	// @RequestMapping("/service-instances/{applicationName}")
	// public List<ServiceInstance> serviceInstancesByApplicationName(
	// 		@PathVariable String applicationName) {
	// 	return this.discoveryClient.getInstances(applicationName);
	// }

}
