package com.e3mall.front.common.pojo;

import java.io.Serializable;
import java.util.List;

import com.e3mall.front.domain.TbOrder;
import com.e3mall.front.domain.TbOrderItem;
import com.e3mall.front.domain.TbOrderShipping;


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
