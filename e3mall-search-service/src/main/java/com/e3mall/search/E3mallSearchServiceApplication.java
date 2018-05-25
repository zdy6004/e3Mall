package com.e3mall.search;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.e3mall.search.**"})
public class E3mallSearchServiceApplication {
	@Bean
    public Queue queue() {
        return new Queue("newItem");
    }

	public static void main(String[] args) {
		SpringApplication.run(E3mallSearchServiceApplication.class, args);
	}
}
