package com.e3mall.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class E3mallEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(E3mallEurekaServerApplication.class, args);
	}
}
