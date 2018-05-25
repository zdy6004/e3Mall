package com.e3mall.content.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.e3mall.content.common.utils.E3Result;
import com.e3mall.content.domain.TbContent;

public interface ContentService {

	E3Result save(TbContent content);

	Map findAllByCategoryId(int rows, int page, long categoryId);

	E3Result delete(String ids);

	List<TbContent> findAllByCategoryId(long LUNBO_ID);

}
