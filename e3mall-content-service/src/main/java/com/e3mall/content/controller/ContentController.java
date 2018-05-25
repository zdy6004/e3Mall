package com.e3mall.content.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e3mall.content.common.utils.E3Result;
import com.e3mall.content.domain.TbContent;
import com.e3mall.content.service.ContentService;



@RestController
public class ContentController {
	@Autowired
	private ContentService contentService;
	@RequestMapping("/admin/rest/content/save")
	
	public E3Result save(@RequestBody TbContent content){
		E3Result resut = contentService.save(content);
		return resut.ok();
	}
	@RequestMapping("/admin/rest/content/edit")
	
	public E3Result edit(@RequestBody TbContent content){
		E3Result resut = contentService.save(content);
		return resut.ok();
	}
	@RequestMapping("/admin/rest/content/delete")
	public E3Result delete(@RequestParam("ids") String ids){
		E3Result resut = contentService.delete(ids);
		return resut.ok();
	}
	@RequestMapping(value = "/admin/rest/content/query/list", method = RequestMethod.GET)
	public Map showContentList(@RequestParam("rows") int rows,
			@RequestParam("page") int page,@RequestParam("categoryId") long categoryId){
		Map map = contentService.findAllByCategoryId(rows, page, categoryId);
		
		return map;
	}
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public List<TbContent> findAllByCategoryId(@RequestParam("lUNBO_ID") long lUNBO_ID){
		List<TbContent> contentList = contentService.findAllByCategoryId(lUNBO_ID);
		return contentList;
	}
	
	
}
