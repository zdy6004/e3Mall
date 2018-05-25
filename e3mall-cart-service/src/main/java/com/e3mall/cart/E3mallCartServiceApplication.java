package com.e3mall.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.e3mall.cart.*"})
public class E3mallCartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(E3mallCartServiceApplication.class, args);
	}
}
