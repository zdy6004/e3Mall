package com.e3mall.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.e3mall.front.*"})
public class E3mallFrontServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(E3mallFrontServiceApplication.class, args);
	}
	
}
