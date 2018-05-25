package com.e3mall.item.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.e3mall.item.common.pojo.DataGridResult;
import com.e3mall.item.common.utils.E3Result;
import com.e3mall.item.domain.TbItem;



public interface ItemService {

	List<TbItem> findAll(Pageable pageable);
	long count();
	void save(TbItem item, String desc);
	void deleteItemBatch(String ids);
	void instockItemBatch(String ids);
	void reshelfItemBatch(String ids);
	void update(TbItem item);
	Map<String, Object> findItemByItemId(long itemId);

	
	

}
