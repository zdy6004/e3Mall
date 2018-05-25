package com.e3mall.admin.client;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.e3mall.admin.domain.TbItem;
import com.sun.jersey.core.header.MediaTypes;
@FeignClient(name = "e3mall-item-service")
public interface ItemServiceClient {
	@RequestMapping(value = "/admin/rest/item/list", method = RequestMethod.GET,consumes = MediaTypes.WADL_JSON_STRING)
	Map findAll(@RequestParam("page") int page, @RequestParam("rows") int rows);
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/item/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void save(TbItem item,@RequestParam("desc") String desc);

	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/item/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void deleteItemBatch(@RequestParam("ids") String ids);

	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/item/instock", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void instockItemBatch(@RequestParam("ids") String ids);

	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/item/reshelf", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void reshelfItemBatch(@RequestParam("ids") String ids);

	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/item/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void update(TbItem item);
	

}
