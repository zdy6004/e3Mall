package com.e3mall.sleuth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
@EnableDiscoveryClient
public class E3mallSleuthServiceApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(E3mallSleuthServiceApplication.class, args);
	}
}
