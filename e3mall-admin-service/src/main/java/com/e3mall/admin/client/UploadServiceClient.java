package com.e3mall.admin.client;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sun.jersey.core.header.MediaTypes;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@FeignClient(name = "e3mall-upload-service", configuration = UploadServiceClient.MultipartSupportConfig.class)
public interface UploadServiceClient {

	
	@RequestMapping(value = "/admin/rest/pic/upload", headers = "Content-Type= multipart/form-data",method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	String uploadPictrue(MultipartFile uploadFile);
	

    public class MultipartSupportConfig {
        @Autowired  
        private ObjectFactory<HttpMessageConverters> messageConverters;  
        @Bean  
        public Encoder feignFormEncoder() {  
            return new SpringFormEncoder(new SpringEncoder(messageConverters));  
        }  
    }
}
