package com.e3mall.sso;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


//import org.springframework.context.annotation.EnableMBeanExport;
//import org.springframework.context.annotation.Import;
//import org.springframework.jmx.support.RegistrationPolicy;

//import com.github.tobato.fastdfs.FdfsClientConfig;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.e3mall.sso.*"})
public class E3mallSsoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(E3mallSsoServiceApplication.class, args);
	}
}
