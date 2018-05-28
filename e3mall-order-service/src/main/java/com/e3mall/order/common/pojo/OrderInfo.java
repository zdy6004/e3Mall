package com.e3mall.order.common.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;


import com.e3mall.order.domain.TbOrder;
import com.e3mall.order.domain.TbOrderItem;
import com.e3mall.order.domain.TbOrderShipping;


public class OrderInfo extends TbOrder implements Serializable {

	
	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
}
