package com.e3mall.front.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.e3mall.front.domain.TbContent;
import com.e3mall.front.domain.TbItem;
import com.sun.jersey.core.header.MediaTypes;

@FeignClient(value = "e3mall-content-service")
public interface ContentServiceClient {

	@RequestMapping(value = "/index", method = RequestMethod.GET,consumes = MediaTypes.WADL_JSON_STRING)
	List<TbContent> findAllByCid(@RequestParam("lUNBO_ID") long lUNBO_ID);
	
}
