package com.e3mall.contentCat.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.contentCat.common.utils.E3Result;
import com.e3mall.contentCat.domain.TbContentCategory;
import com.e3mall.contentCat.repository.ContentCategoryRepository;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private ContentCategoryRepository contentCategoryRepository;
	
	@Override
	public List<TbContentCategory> findAllByParentId(long id) {
		return contentCategoryRepository.findAllByParentId(id);
		
	}

	@Override
	public E3Result addContentCategory(TbContentCategory contentCategory) {
		E3Result result = new E3Result();
		contentCategory.setCreated(new Date());
		contentCategory.setIsParent(false);
		TbContentCategory parentcontentCategory = contentCategoryRepository.findOne(contentCategory.getParentId());
		contentCategory.setStatus(1);
		contentCategory.setUpdated(new Date());
		List<TbContentCategory> list = contentCategoryRepository.findAllByParentId(contentCategory.getParentId());
		
		Long id = contentCategoryRepository.save(contentCategory).getId();
		if(parentcontentCategory.getIsParent() == false){
			parentcontentCategory.setIsParent(true);
			contentCategoryRepository.save(parentcontentCategory);
		}
		contentCategory.setId(id);
		result.setData(contentCategory);
		result.setStatus(200);
		return result.ok();
	}

	@Override
	public E3Result updateContentCategory(Long id, String name) {
		E3Result result = new E3Result();
		TbContentCategory contentCategory = contentCategoryRepository.findOne(id);
		contentCategory.setName(name);
		contentCategoryRepository.save(contentCategory);
		return result.ok();
	}

	@Override
	public E3Result deleteContentCategory(Long id) {
		E3Result result = new E3Result();
		TbContentCategory contentCategory = contentCategoryRepository.findOneById(id);
		if(contentCategory.getIsParent() == true){
			return result.build(500, "还有子目录，不能删除");
		}
		Long parentId = contentCategory.getParentId();
		TbContentCategory parentContentCategory = contentCategoryRepository.findOne(parentId);
		contentCategoryRepository.delete(id);
		int num = contentCategoryRepository.countByParentId(parentId);
		if(num == 0){
			parentContentCategory.setIsParent(false);
			contentCategoryRepository.save(parentContentCategory);
		}
		return result.ok();
	}

}
