package com.e3mall.contentCat.service;

import java.util.List;

import com.e3mall.contentCat.common.utils.E3Result;
import com.e3mall.contentCat.domain.TbContentCategory;

public interface ContentCategoryService {

	List<TbContentCategory> findAllByParentId(long id);

	E3Result addContentCategory(TbContentCategory contentCategory);

	E3Result updateContentCategory(Long id, String name);

	E3Result deleteContentCategory(Long id);

}
