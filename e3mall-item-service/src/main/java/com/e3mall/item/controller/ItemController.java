package com.e3mall.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e3mall.item.domain.TbItem;
import com.e3mall.item.service.ItemService;


@RestController
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@RequestMapping(value = "/admin/rest/item/list", method = RequestMethod.GET)
	public Map findAll(@RequestParam("page") int page, @RequestParam("rows") int rows){
		Map map = new HashMap();
		Pageable pageable = new PageRequest(page-1, rows);
		List<TbItem> list = itemService.findAll(pageable);
		long count = itemService.count();
		map.put("list", list);
		map.put("count", count);
		return map;
	}
	
	@RequestMapping(value = "/admin/rest/item/save", method = RequestMethod.POST)
	public void saveItem(@RequestBody TbItem item,@RequestParam("desc") String desc){
		itemService.save(item,desc);
		
	}
	@RequestMapping("/admin/rest/item/delete")
	public void deleteItemBatch(@RequestParam("ids") String ids){
		itemService.deleteItemBatch(ids);
	}
	@RequestMapping(value = "/admin/rest/item/update", method = RequestMethod.POST)
	public void updateItem(@RequestBody TbItem item){
		itemService.update(item);
	}
	@RequestMapping("/admin/rest/item/instock")
	public void instockItemBatch(@RequestParam("ids") String ids){
		itemService.instockItemBatch(ids);
		
	}
	@RequestMapping("/admin/rest/item/reshelf")
	public void reshelfItemBatch(@RequestParam("ids") String ids){
		itemService.reshelfItemBatch(ids);
		
	}
	@RequestMapping(value = {"/front/item/{itemId}", "/front/cart/add/{itemId}"}, method = RequestMethod.GET)
	public Map<String, Object> showItem(@PathVariable("itemId") long itemId){
		Map<String, Object> map = itemService.findItemByItemId(itemId);
		return map;
		
	}
}
