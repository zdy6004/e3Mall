package com.e3mall.front.client;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.e3mall.front.common.pojo.OrderInfo;
import com.e3mall.front.common.utils.E3Result;
import com.sun.jersey.core.header.MediaTypes;

@FeignClient(name = "e3mall-order-service")
public interface OrderServiceClient {
	
	@RequestMapping(value = "/front/order/create", method = RequestMethod.POST,consumes = MediaTypes.WADL_JSON_STRING)
	E3Result orderCreate(OrderInfo orderInfo);
	

}
