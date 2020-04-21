package com.kunal.digib.app.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DigibAppDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigibAppDiscoveryServiceApplication.class, args);
	}

}
