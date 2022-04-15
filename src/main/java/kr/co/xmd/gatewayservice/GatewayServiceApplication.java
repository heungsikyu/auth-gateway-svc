package kr.co.xmd.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
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
