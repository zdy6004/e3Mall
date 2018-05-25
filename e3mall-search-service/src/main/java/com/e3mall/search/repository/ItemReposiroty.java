package com.e3mall.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.e3mall.search.common.pojo.SearchItem;
import com.e3mall.search.domain.TbItem;




public interface ItemReposiroty extends JpaRepository<TbItem, Long> {


	
	@Query (value = "select new com.e3mall.search.common.pojo.SearchItem (t.id, t.title, t.sellPoint as sell_point, t.price, t.image, tc.name as category_name) from TbItem t, TbItemCat tc where t.status = 1 and t.cid = tc.id")
	List<SearchItem> findItemList();
	@Query (value = "select new com.e3mall.search.common.pojo.SearchItem (t.id, t.title, t.sellPoint as sell_point, t.price, t.image, tc.name as category_name) from TbItem t, TbItemCat tc where t.status = 1 and t.cid = tc.id and t.id = :id")
	SearchItem findSearchItem(@Param("id") long id);
	
//	@Query (value = "select new com.e3mall.search.common.pojo.SearchItem (t.id, t.title, t.sellPoint as sell_point, t.price, t.image, tc.name as category_name) from TbItem t left outer join t.category tc where t.status = 1")
//	List<SearchItem> findItemList();
//	

}
