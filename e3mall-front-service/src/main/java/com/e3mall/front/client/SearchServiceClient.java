package com.e3mall.front.client;

import java.util.List;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.e3mall.front.common.pojo.SearchResult;
import com.e3mall.front.domain.TbItem;
import com.sun.jersey.core.header.MediaTypes;

@FeignClient(value = "e3mall-search-service")
public interface SearchServiceClient {

	@RequestMapping(value = "/front/search", method = RequestMethod.GET,consumes = MediaTypes.WADL_JSON_STRING)
	SearchResult findAllByKeyword(@RequestParam("keyword") String keyword, @RequestParam("page") int page, @RequestParam("rows") int rows);
	

}
