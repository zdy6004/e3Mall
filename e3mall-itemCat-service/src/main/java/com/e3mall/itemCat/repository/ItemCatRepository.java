package com.e3mall.itemCat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e3mall.itemCat.domain.TbItemCat;


public interface ItemCatRepository extends JpaRepository<TbItemCat, Long> {

	List<TbItemCat> findAllByParentId(long id);

}
