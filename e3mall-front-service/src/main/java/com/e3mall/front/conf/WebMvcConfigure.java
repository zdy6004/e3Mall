package com.e3mall.front.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.e3mall.front.interceptor.LoginInterceptor;


@Configuration
public class WebMvcConfigure extends WebMvcConfigurerAdapter{
	@Bean
	  public LoginInterceptor loginInterceptor() {
	    return new LoginInterceptor();
	  }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//不能new LoginInterceptor（）,要用对象loginInterceptor，否则会出现空值
		 // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
		registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
														
		super.addInterceptors(registry);
	}

}
