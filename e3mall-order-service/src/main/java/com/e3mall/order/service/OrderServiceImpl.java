package com.e3mall.order.service;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.order.common.jedis.RedisClient;
import com.e3mall.order.common.pojo.OrderInfo;
import com.e3mall.order.common.utils.E3Result;
import com.e3mall.order.domain.TbOrder;
import com.e3mall.order.domain.TbOrderItem;
import com.e3mall.order.domain.TbOrderShipping;
import com.e3mall.order.repository.OrderItemRepository;
import com.e3mall.order.repository.OrderRepository;
import com.e3mall.order.repository.OrderShippingRepository;
import com.mysql.fabric.xmlrpc.base.Data;

@Service
public class OrderServiceImpl implements OrderService {
	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private RedisClient redisClient;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderShippingRepository orderShippingRepository;
	@Override
	public E3Result orderCreate(OrderInfo orderInfo) {
		if(!redisClient.exists("ORDER_ID_GEN")){
			redisClient.set("ORDER_ID_GEN", "100544");
		}
		String string = redisClient.incr("ORDER_ID_GEN").toString();
		int orderId = Integer.parseInt(string);
		TbOrder order = new TbOrder();
		orderInfo.setOrderId(string);
		orderInfo.setStatus(1);
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		order.setOrderId(string);
		order.setStatus(1);
		order.setCreateTime(new Date());
		order.setUpdateTime(new Date());
		orderRepository.save(order);
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			String string2 = redisClient.incr("100").toString();
			int odId = Integer.parseInt(string2);
			tbOrderItem.setId(string2);
			tbOrderItem.setOrderId(string);
			orderItemRepository.save(tbOrderItem);
		}
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(string);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingRepository.save(orderShipping);
		
		return E3Result.ok(orderId);
	}

	

}