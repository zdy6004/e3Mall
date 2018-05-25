package com.e3mall.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e3mall.cart.domain.TbItem;


public interface ItemReposiroty extends JpaRepository<TbItem, Long> {

	List<TbItem> findAllById(long itemId);

}
