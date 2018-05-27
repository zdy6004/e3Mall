package com.e3mall.admin.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.e3mall.admin.domain.TbItemCat;
@FeignClient(name = "e3mall-itemCat-service")
public interface ItemCatServiceClient {
	@RequestMapping(method = RequestMethod.GET, value = "/item/rest/cat/list")
	List<TbItemCat> findAllByParentId(long id);
}
