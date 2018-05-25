package com.e3mall.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;



@SpringBootApplication
@EnableConfigServer
public class E3mallConfigServiceApplication {
	
	
//	public static void main(String[] args) {
//		 new SpringApplicationBuilder(E3mallConfigServiceApplication.class).web(true).run(args);
//	}
	public static void main(String[] args) {
		SpringApplication.run(E3mallConfigServiceApplication.class, args);
	}
}
