package com.e3mall.content.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.e3mall.content.common.jedis.RedisClient;
import com.e3mall.content.common.utils.E3Result;
import com.e3mall.content.common.utils.JsonUtils;
import com.e3mall.content.domain.TbContent;
import com.e3mall.content.repository.ContentRepository;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${CONTENT_LIST.CONTENT_LIST_NAME}")
	private String CONTENT_LIST_NAME;
	
	@Autowired
	private ContentRepository contentRepository;
	@Autowired
	private RedisClient redisClient;

	@Override
	@Transactional
	public E3Result save(TbContent content) {
		E3Result result = new E3Result();
		content.setCreated(new Date());
		content.setUpdated(new Date());
	contentRepository.save(content);
	String hget = (String) redisClient.hmGet(CONTENT_LIST_NAME, content.getCategoryId()+"");
	if(StringUtils.isNotBlank(hget)){
		redisClient.hmRemove(CONTENT_LIST_NAME, content.getCategoryId()+"");
	}
	return result.ok();
	}

	@Override
	public Map findAllByCategoryId(int rows, int page, long categoryId) {
		Map map = new HashMap();
		Pageable pageable = new PageRequest(page-1, rows);
        //创建实例
        //Example<TbContent> ex = Example.of(content); 
		//List<TbContent> list = contentRepository.findAll(ex, pageable).getContent();
		Page<TbContent> findAll = contentRepository.findAll(pageable,categoryId);
		List<TbContent> list = findAll.getContent();
		long count = contentRepository.countByCategoryId(categoryId);
		map.put("list", list);
		map.put("count", count);
		return map;
	}

	@Override
	@Transactional
	public E3Result delete(String ids) {
		E3Result result = new E3Result();
		String[] split = ids.split(",");
		for (String id : split) {
			TbContent content = contentRepository.findOne(Long.parseLong(id));
			String hget = (String) redisClient.hmGet(CONTENT_LIST_NAME, content.getCategoryId()+"");
			if(StringUtils.isNotBlank(hget)){
				redisClient.hmRemove(CONTENT_LIST_NAME, content.getCategoryId()+"");
			}
			contentRepository.delete(Long.parseLong(id));
		}
		return result.ok();
	}

	@Override
	public List<TbContent> findAllByCategoryId(long LUNBO_ID) {
		
		try {
			System.out.println(CONTENT_LIST_NAME);
			String hget = (String) redisClient.hmGet(CONTENT_LIST_NAME, LUNBO_ID+"");
			if(StringUtils.isNotBlank(hget)){
				List<TbContent> contentList = JsonUtils.jsonToList(hget, TbContent.class);
				System.out.println("查询缓存");
				return contentList;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("查询数据库");
		List<TbContent> contentList = contentRepository.findAllByCategoryId(LUNBO_ID);
		
		try {
			redisClient.hmSet(CONTENT_LIST_NAME, LUNBO_ID+"", JsonUtils.objectToJson(contentList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentList;
	}
	
}
