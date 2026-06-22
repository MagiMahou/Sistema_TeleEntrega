package com.bcopstein.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	@org.springframework.cloud.client.loadbalancer.LoadBalanced
	public org.springframework.web.reactive.function.client.WebClient.Builder webClientBuilder() {
		return org.springframework.web.reactive.function.client.WebClient.builder();
	}
}
