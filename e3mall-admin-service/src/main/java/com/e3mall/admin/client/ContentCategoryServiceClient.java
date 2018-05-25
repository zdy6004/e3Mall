package com.e3mall.admin.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.e3mall.admin.common.utils.E3Result;
import com.e3mall.admin.domain.TbContent;
import com.e3mall.admin.domain.TbContentCategory;

@FeignClient(name = "e3mall-contentCat-service")
public interface ContentCategoryServiceClient {
	@RequestMapping(method = RequestMethod.GET, value = "/admin/rest/content/category/list")
	List<TbContentCategory> findAllByParentId(long id);
	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/content/category/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	E3Result save(TbContentCategory contentCategory);
	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/content/category/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	E3Result update(@RequestParam("id") Long id, @RequestParam("name") String name);
	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/content/category/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	E3Result delete(@RequestParam("id") Long id);
	
}
