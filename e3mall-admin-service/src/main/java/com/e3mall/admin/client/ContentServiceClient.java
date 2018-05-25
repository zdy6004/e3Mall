package com.e3mall.admin.client;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.e3mall.admin.common.utils.E3Result;
import com.e3mall.admin.domain.TbContent;
import com.sun.jersey.core.header.MediaTypes;

@FeignClient(name = "e3mall-content-service")
public interface ContentServiceClient {
	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/content/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	E3Result save(TbContent content);

	@RequestMapping(value = "/admin/rest/content/query/list", method = RequestMethod.GET,consumes = MediaTypes.WADL_JSON_STRING)
	Map findAll(@RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("categoryId") long categoryId);

	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/content/edit", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	E3Result edit(TbContent content);
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/content/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	E3Result delete(@RequestParam("ids") String ids);
}
