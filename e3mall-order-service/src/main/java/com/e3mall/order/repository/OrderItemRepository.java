package com.e3mall.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e3mall.order.domain.TbOrderItem;
import com.e3mall.order.domain.TbOrderShipping;

public interface OrderItemRepository extends JpaRepository<TbOrderItem, Integer> {

}
