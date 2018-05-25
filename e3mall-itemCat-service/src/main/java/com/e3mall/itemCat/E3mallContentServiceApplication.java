package com.e3mall.itemCat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
public class E3mallContentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(E3mallContentServiceApplication.class, args);
	}
}
