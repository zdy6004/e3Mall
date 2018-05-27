package com.e3mall.front.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.e3mall.front.common.utils.E3Result;
import com.e3mall.front.domain.TbItem;
import com.sun.jersey.core.header.MediaTypes;

@FeignClient(value = "e3mall-cart-service")
public interface CartServiceClient {
	@RequestMapping(value = "/front/cart/cart", method = RequestMethod.POST,consumes = MediaTypes.WADL_JSON_STRING)
	void mergeCartList();
	@RequestMapping(value = "/front/cart/delete/{itemId}", method = RequestMethod.GET,consumes = MediaTypes.WADL_JSON_STRING)
	E3Result deleteCartItem(@PathVariable("itemId") long itemId);
	@RequestMapping(value = "/front/order/create", method = RequestMethod.POST,consumes = MediaTypes.WADL_JSON_STRING)
	E3Result clearCartList(@RequestParam("userId") long userId);
	

}
