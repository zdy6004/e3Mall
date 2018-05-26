package com.e3mall.front.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.e3mall.front.common.pojo.Item;
import com.e3mall.front.common.pojo.SearchResult;
import com.e3mall.front.common.utils.E3Result;
import com.e3mall.front.domain.TbContent;
import com.e3mall.front.domain.TbItem;
import com.e3mall.front.domain.TbItemDesc;

public interface FrontService {

	List<TbContent> findAllByCid(long lUNBO_ID);

	SearchResult findAllByKeyword(String keyword, int page, int rows);

	Map<String, Object> findItemByPid(long itemId);

	List<TbItem> findCartList(HttpServletRequest request);

	E3Result deleteCartItem(long itemId);

	E3Result deleteCartItemToRedis(long userId, long itemId);

//	void addCart(long itemId, int num);

	//TbItemDesc findItemDescByPid(long itemId);

}
