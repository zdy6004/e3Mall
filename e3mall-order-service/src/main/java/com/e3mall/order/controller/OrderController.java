package com.e3mall.order.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.e3mall.order.common.pojo.OrderInfo;
import com.e3mall.order.common.utils.E3Result;
import com.e3mall.order.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	@RequestMapping(value = "/front/order/create", method = RequestMethod.POST)
	public E3Result orderCreate(@RequestBody OrderInfo orderInfo){
		System.out.println("=====");
		E3Result result = orderService.orderCreate(orderInfo);
		return result;
	}
}
