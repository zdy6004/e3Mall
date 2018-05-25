package com.e3mall.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.e3mall.admin.client.ContentCategoryServiceClient;
import com.e3mall.admin.client.ContentServiceClient;
import com.e3mall.admin.client.ItemCatServiceClient;
import com.e3mall.admin.client.ItemServiceClient;
import com.e3mall.admin.client.SearchServiceClient;
import com.e3mall.admin.client.UploadServiceClient;
import com.e3mall.admin.common.pojo.DataGridResult;
import com.e3mall.admin.common.pojo.EasyUiTreeResult;
import com.e3mall.admin.common.utils.E3Result;
import com.e3mall.admin.common.utils.JsonUtils;
import com.e3mall.admin.domain.TbContent;
import com.e3mall.admin.domain.TbContentCategory;
import com.e3mall.admin.domain.TbItem;
import com.e3mall.admin.domain.TbItemCat;
import com.e3mall.admin.common.utils.IDUtils;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private ItemCatServiceClient itemCatServiceClient;
	@Autowired
	private ContentCategoryServiceClient contentCategoryClient;
	@Autowired
	private ContentServiceClient contentClient;
	@Autowired
	private ItemServiceClient itemServiceClient;
	@Autowired
	private UploadServiceClient uploadServiceClient;
	@Autowired
	private SearchServiceClient searchServiceClient;
//	@Autowired
//	private Queue queue;
	@Autowired
	private AmqpTemplate rabbitTemplate;
	@Override
	public List<EasyUiTreeResult> findCatList(long id) {
		List<EasyUiTreeResult> TreeResults = new ArrayList<>();
		List<TbItemCat> CatList = itemCatServiceClient.findAllByParentId(id);
		for (TbItemCat tbItemCat : CatList) {
			EasyUiTreeResult result = new EasyUiTreeResult();
			result.setId(tbItemCat.getId());
			result.setText(tbItemCat.getName());
			List<TbItemCat> list = itemCatServiceClient.findAllByParentId(tbItemCat.getId());
			result.setState(list != null && list.size() > 0?"closed":"open");
			TreeResults.add(result);
		}
		return TreeResults;
	}

	@Override
	public List<EasyUiTreeResult> findContentCategory(long id) {
		List<EasyUiTreeResult> TreeResults = new ArrayList<>();
		List<TbContentCategory> CatList = contentCategoryClient.findAllByParentId(id);
		for (TbContentCategory contentCategory : CatList) {
			EasyUiTreeResult result = new EasyUiTreeResult();
			result.setId(contentCategory.getId());
			result.setText(contentCategory.getName());
			List<TbContentCategory> list = contentCategoryClient.findAllByParentId(contentCategory.getId());
			result.setState(list != null && list.size() > 0?"closed":"open");
			TreeResults.add(result);
		}
		return TreeResults;
	}

	@Override
	public E3Result save(TbContentCategory contentCategory) {
		try {
			E3Result result = new E3Result(); 
			result = contentCategoryClient.save(contentCategory);
			return result;
		} catch (Exception e) {
			E3Result result = new E3Result(); 
			e.printStackTrace();
			return result;
		}
	}
	
	@Override
	public DataGridResult findItemList(int page, int rows) {
		DataGridResult result = new DataGridResult();
		//Pageable pageable = new PageRequest(page, row);
		Map map = itemServiceClient.findAll(page, rows);
		List<TbItem> content = (List<TbItem>) map.get("list");
		int count = (int) map.get("count");
		result.setRows(content);
		result.setTotal((int) count);
		return result;
	}

	@Override
	@Transactional
	public E3Result save(TbItem item, String desc) {
		E3Result result =  new E3Result();
		try {
			long id = IDUtils.genItemId();
			item.setId(id);
			itemServiceClient.save(item,desc);
//			rabbitTemplate.convertAndSend("newItem", id+"");
			return result.ok();
		} catch (Exception e) {
			return result.build(500, "添加失败");
		}
	}
//	@Override
//	@Transactional
//	public E3Result save(TbItem item, String desc) {
//		E3Result result =  new E3Result();
//		try {
//			long id = IDUtils.genItemId();
//			item.setId(id);
//			itemServiceClient.save(item,desc);
//			this.jmsMessagingTemplate.convertAndSend(this.topic, id);
//			return result.ok();
//		} catch (Exception e) {
//			return result.build(500, "添加失败");
//		}
//	}
	
	@Override
	public E3Result update(TbItem item) {
		E3Result result =  new E3Result();
		try {
			itemServiceClient.update(item);
			result.setStatus(200);
			return result;
		} catch (Exception e) {
			result.setStatus(100);
			return result;
		}
	}
	
	@Override
	public E3Result deleteItemBatch(String ids) {
		E3Result result = new E3Result();
		try {
			itemServiceClient.deleteItemBatch(ids);
			result.setStatus(200);
			return result;
		} catch (Exception e) {
			result.setStatus(100);
			return result;
		}
		
	
	}

	@Override
	public E3Result instockItemBatch(String ids) {
		E3Result result = new E3Result();
		try {
			itemServiceClient.instockItemBatch(ids);
			result.setStatus(200);
			return result;
		} catch (Exception e) {
			result.setStatus(100);
			return result;
		}
	}

	@Override
	public E3Result reshelfItemBatch(String ids) {
		E3Result result = new E3Result();
		try {
			itemServiceClient.reshelfItemBatch(ids);
			result.setStatus(200);
			return result;
		} catch (Exception e) {
			result.setStatus(100);
			return result;
		}
	}
	@Override
	public String uploadPicture(MultipartFile uploadFile) {
		
		try {
			String url = uploadServiceClient.uploadPictrue(uploadFile);
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			Map result = new HashMap<>();
			e.printStackTrace();
			result.put("error", "1");
			result.put("text", "对不起,上传失败");
			return JsonUtils.objectToJson(result);
		}
		
	}
	@Override
	public E3Result update(Long id, String name){
		
		try {
			E3Result result = new E3Result(); 
			result = contentCategoryClient.update(id,name);

			
			return result;
		} catch (Exception e) {
			E3Result result = new E3Result(); 
			e.printStackTrace();
			
			return result;
		}
		
	}

	@Override
	public E3Result delete(Long id) {
		try {
			E3Result result = new E3Result(); 
			result = contentCategoryClient.delete(id);

			return result.ok();
		} catch (Exception e) {
			E3Result result = new E3Result(); 
			e.printStackTrace();
			
			return result;
		}
		 
	}

	@Override
	public E3Result saveContent(TbContent content) {
		try {
			 
			 E3Result result = contentClient.save(content);
			return result;
		} catch (Exception e) {
			E3Result result = new E3Result(); 
			e.printStackTrace();
			return result;
		}
	}
	@Override
	public E3Result editContent(TbContent content) {
		try {
			
			E3Result result = contentClient.edit(content);
			return result;
		} catch (Exception e) {
			E3Result result = new E3Result(); 
			e.printStackTrace();
			return result;
		}
	}
	@Override
	public E3Result deleteContent(String ids) {
		try {
			
			E3Result result = contentClient.delete(ids);
			return result;
		} catch (Exception e) {
			E3Result result = new E3Result(); 
			e.printStackTrace();
			return result;
		}
	}

	@Override
	public DataGridResult findContentList(int page, int rows,long categoryId) {
		DataGridResult result = new DataGridResult();
		//Pageable pageable = new PageRequest(page, row);
		Map map = contentClient.findAll(page, rows,categoryId);
		List<TbContent> content = (List<TbContent>) map.get("list");
		int count = (int) map.get("count");
		result.setRows(content);
		result.setTotal((int) count);
		return result;
	}

	@Override
	public E3Result importIndex() {
		int in = 0;
		E3Result result = searchServiceClient.importIndex();
		
		return result;
	}

	
}
