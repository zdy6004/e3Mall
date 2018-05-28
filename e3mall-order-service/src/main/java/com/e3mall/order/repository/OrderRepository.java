package com.e3mall.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e3mall.order.domain.TbOrder;

public interface OrderRepository extends JpaRepository<TbOrder, Integer> {

}
