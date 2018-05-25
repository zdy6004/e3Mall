package com.e3mall.admin;



import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


//import org.springframework.context.annotation.EnableMBeanExport;
//import org.springframework.context.annotation.Import;
//import org.springframework.jmx.support.RegistrationPolicy;

//import com.github.tobato.fastdfs.FdfsClientConfig;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.e3mall.admin.*"})
public class E3mallAdminServiceApplication {
//	@Bean
//    public Queue queue() {
//        return new Queue("newItem");
//    }
	public static void main(String[] args) {
		SpringApplication.run(E3mallAdminServiceApplication.class, args);
	}
}
