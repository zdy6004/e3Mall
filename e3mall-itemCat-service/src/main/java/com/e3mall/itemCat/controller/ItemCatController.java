package com.e3mall.itemCat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.itemCat.common.pojo.EasyUiTreeResult;
import com.e3mall.itemCat.domain.TbItemCat;
import com.e3mall.itemCat.service.ItemCatService;


@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	@RequestMapping(value = "/item/rest/cat/list")
	@ResponseBody
	public List<TbItemCat> showCatList(@RequestBody long id){
		List<TbItemCat> catList = itemCatService.findAllByParentId(id);
		return catList;
	}
}
