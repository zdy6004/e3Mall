package com.e3mall.admin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.e3mall.admin.common.pojo.DataGridResult;
import com.e3mall.admin.common.pojo.EasyUiTreeResult;
import com.e3mall.admin.common.utils.E3Result;
import com.e3mall.admin.domain.TbContent;
import com.e3mall.admin.domain.TbContentCategory;
import com.e3mall.admin.domain.TbItem;
import com.e3mall.admin.service.AdminService;

@RestController
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	
	@RequestMapping(value = "/rest/item/cat/list")
	public List<EasyUiTreeResult> showCatList(@RequestParam(value = "id", defaultValue = "0") long id){
		List<EasyUiTreeResult> resut = adminService.findCatList(id);
		return resut;
	}

	@RequestMapping(value = "/rest/item/list", method = RequestMethod.GET)
	public DataGridResult showItemList(@RequestParam("rows") int rows,
			@RequestParam("page") int page){
		DataGridResult result =	adminService.findItemList(page,rows);
		return result;
	}
	
	@RequestMapping(value = "/rest/item/save", method = RequestMethod.POST)
	public E3Result saveItem(TbItem item, String desc){
		System.out.println(item);
		E3Result result = adminService.save(item,desc);
		return result;
	}
	
	@RequestMapping(value = "/rest/item/update", method = RequestMethod.POST)
	public E3Result updateItem(TbItem item){
		E3Result result = adminService.update(item);
		return result;
	}
	
	@RequestMapping(value="/rest/pic/upload", method = RequestMethod.POST)
	public String uploadPic(@RequestParam("uploadFile") MultipartFile uploadFile){
			String result = adminService.uploadPicture(uploadFile);
			return result;
		
	}
	@RequestMapping("/rest/item/delete")
	public E3Result deleteItemBatch(@RequestParam("ids") String ids){
		E3Result result = adminService.deleteItemBatch(ids);
		return result;
	}
	@RequestMapping("/rest/item/instock")
	public E3Result instockItemBatch(@RequestParam("ids") String ids){
		E3Result result = adminService.instockItemBatch(ids);
		return result;
	}
	@RequestMapping("/rest/item/reshelf")
	public E3Result reshelfItemBatch(@RequestParam("ids") String ids){
		E3Result result = adminService.reshelfItemBatch(ids);
		return result;
	}
	@RequestMapping(value = "/rest/content/category/list")
	public List<EasyUiTreeResult> showContentCategory(@RequestParam(value = "id", defaultValue = "0") long id){
		List<EasyUiTreeResult> resut = adminService.findContentCategory(id);
		return resut;
	}
	@RequestMapping(value = "/rest/content/category/create", method = RequestMethod.POST)
	public E3Result addContentCategory(TbContentCategory contentCategory){
		E3Result resut = adminService.save(contentCategory);
		return resut;
	}
	@RequestMapping(value = "/rest/content/category/update", method = RequestMethod.POST)
	public E3Result updateContentCategory(Long id, String name){
		E3Result resut = adminService.update(id, name);
		return resut;
	}
	@RequestMapping(value = "/rest/content/category/delete", method = RequestMethod.POST)
	public E3Result deleteContentCategory(Long id){
		E3Result resut = adminService.delete(id);
		return resut;
	}
	@RequestMapping(value = "/rest/content/save", method = RequestMethod.POST)
	public E3Result saveContent(TbContent content){
		E3Result resut = adminService.saveContent(content);
		return resut;
	}
	@RequestMapping(value = "/rest/content/edit", method = RequestMethod.POST)
	public E3Result editContent(TbContent content){
		E3Result resut = adminService.editContent(content);
		return resut;
	}
	@RequestMapping(value = "/rest/content/delete", method = RequestMethod.POST)
	public E3Result deleteContent(String ids){
		E3Result resut = adminService.deleteContent(ids);
		return resut;
	}
	@RequestMapping(value = "/rest/content/query/list", method = RequestMethod.GET)
	public DataGridResult showContentList(@RequestParam("rows") int rows,
			@RequestParam("page") int page,@RequestParam(value = "categoryId", defaultValue = "0") long categoryId){
		DataGridResult result =	adminService.findContentList(page,rows,categoryId);
		return result;
	}
	@RequestMapping(value ="/rest/index/item/import", method = RequestMethod.POST)
	public E3Result importIndex(){
		E3Result result = adminService.importIndex();
		return result;
	}
	
}
