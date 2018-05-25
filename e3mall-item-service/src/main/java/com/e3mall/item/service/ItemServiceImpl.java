package com.e3mall.item.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.e3mall.item.common.jedis.RedisClient;
import com.e3mall.item.common.pojo.DataGridResult;
import com.e3mall.item.common.utils.E3Result;
import com.e3mall.item.common.utils.IDUtils;
import com.e3mall.item.common.utils.JsonUtils;
import com.e3mall.item.domain.TbContent;
import com.e3mall.item.domain.TbItem;
import com.e3mall.item.domain.TbItemDesc;
import com.e3mall.item.repository.ItemDescReposiroty;
import com.e3mall.item.repository.ItemReposiroty;
import com.e3mall.item.repository.contentRepository;



@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemReposiroty itemReposiroty;
	@Autowired
	private ItemDescReposiroty itemDescReposiroty;
	@Autowired
	private RedisClient redisClient;


	@Override
	public List<TbItem> findAll(Pageable pageable) {
		List<TbItem> content = itemReposiroty.findAll(pageable).getContent();
		return content;
	}

	@Override
	public long count() {
		long count = itemReposiroty.count();
		return count;
	}

	@Override
	@Transactional
	public void save(TbItem item, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemReposiroty.save(item);
		itemDesc.setCreated(new Date());
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(new Date());
		itemDescReposiroty.save(itemDesc);
	}
	
	@Override
	@Transactional
	public void update(TbItem item) {
		//TODO param参数没有搞清楚，稍后再做更新
		itemReposiroty.save(item);
	}
	@Override
	@Transactional
	public void deleteItemBatch(String ids) {
		String[] split = ids.split(",");
		for (String id : split) {
			itemReposiroty.delete(Long.parseLong(id));
			itemDescReposiroty.delete(Long.parseLong(id));
		}
		
	}

	@Override
	@Transactional
	public void instockItemBatch(String ids) {
		String[] split = ids.split(",");
		for (String id : split) {
			TbItem tbItem = itemReposiroty.findOne(Long.parseLong(id));
			tbItem.setStatus((byte) 2);
			itemReposiroty.save(tbItem);
		}
		
	}
	@Override
	@Transactional
	public void reshelfItemBatch(String ids) {
		String[] split = ids.split(",");
		for (String id : split) {
			TbItem tbItem = itemReposiroty.findOne(Long.parseLong(id));
			tbItem.setStatus((byte) 1);
			itemReposiroty.save(tbItem);
		}
		
	}

	@Override
	public Map<String, Object> findItemByItemId(long itemId) {
		Map<String, Object> map = new HashMap<>();
		TbItem tbItem = new TbItem();
		TbItemDesc itemDesc = new TbItemDesc();
		try {
			tbItem = (TbItem) redisClient.get("item_"+itemId);
			itemDesc = (TbItemDesc) redisClient.get("itemDesc_"+itemId);
			if(tbItem != null && itemDesc != null){
				String tbItemToJson = JsonUtils.objectToJson(tbItem);
				String itemDescToJson = JsonUtils.objectToJson(itemDesc);
				map.put("tbItem", tbItemToJson);
				map.put("itemDesc", itemDescToJson);
				return map;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tbItem = itemReposiroty.findOne(itemId);
		itemDesc = itemDescReposiroty.findOneByItemId(itemId);
		
		String tbItemToJson = JsonUtils.objectToJson(tbItem);
		String itemDescToJson = JsonUtils.objectToJson(itemDesc);
		map.put("tbItem", tbItemToJson);
		map.put("itemDesc", itemDescToJson);
		
		try {
			redisClient.set("item_"+itemId, tbItem, 60l);
			redisClient.set("itemDesc_"+itemId, itemDesc, 60l);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}



}