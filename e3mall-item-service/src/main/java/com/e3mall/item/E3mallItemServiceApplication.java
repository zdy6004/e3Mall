package com.e3mall.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class E3mallItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(E3mallItemServiceApplication.class, args);
	}
}
