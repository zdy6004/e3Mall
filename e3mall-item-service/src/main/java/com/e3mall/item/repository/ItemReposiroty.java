package com.e3mall.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e3mall.item.domain.TbItem;


public interface ItemReposiroty extends JpaRepository<TbItem, Long> {

	List<TbItem> findAllById(long itemId);

}
