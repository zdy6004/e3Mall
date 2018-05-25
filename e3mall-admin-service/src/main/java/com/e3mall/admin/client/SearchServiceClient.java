package com.e3mall.admin.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.e3mall.admin.common.utils.E3Result;

@FeignClient(name = "e3mall-search-service")
public interface SearchServiceClient {
	@RequestMapping(method = RequestMethod.POST, value = "/admin/rest/index/item/import")
	E3Result importIndex();
}
