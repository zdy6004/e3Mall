package com.e3mall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class E3mallGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(E3mallGatewayServiceApplication.class, args);
	}
}
