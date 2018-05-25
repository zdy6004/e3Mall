package com.e3mall.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e3mall.item.domain.TbItemDesc;


public interface ItemDescReposiroty extends JpaRepository<TbItemDesc, Long> {


	TbItemDesc findAllByItemId(long itemId);

	TbItemDesc findOneByItemId(long itemId);

}
