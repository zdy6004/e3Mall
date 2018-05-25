package com.e3mall.contentCat.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.e3mall.contentCat.common.utils.E3Result;
import com.e3mall.contentCat.domain.TbContentCategory;
import com.e3mall.contentCat.service.ContentCategoryService;



@RestController
public class ContentCatController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	@RequestMapping("/admin/rest/content/category/list")
	@ResponseBody
	public List<TbContentCategory> showCatList(@RequestBody long id){
		List<TbContentCategory> catList = contentCategoryService.findAllByParentId(id);
		return catList;
	}
	@RequestMapping(value = "/admin/rest/content/category/create", method = RequestMethod.POST)
	public E3Result addContentCategory(@RequestBody TbContentCategory contentCategory){
		E3Result result = contentCategoryService.addContentCategory(contentCategory);
		return result;
	}

	@RequestMapping(value = "/admin/rest/content/category/update", method = RequestMethod.POST)
	public E3Result updateContentCategory(@RequestParam("id") Long id, @RequestParam("name") String name){
		E3Result result = contentCategoryService.updateContentCategory(id,name);
		return result;
	}
	@RequestMapping(value = "/admin/rest/content/category/delete", method = RequestMethod.POST)
	public E3Result deleteContentCategory(@RequestParam("id") Long id){
		E3Result result = contentCategoryService.deleteContentCategory(id);
		
		
		return result;
	}
}
