package com.e3mall.front.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.front.client.CartServiceClient;
import com.e3mall.front.client.ContentServiceClient;
import com.e3mall.front.client.ItemServiceClient;
import com.e3mall.front.client.SearchServiceClient;
import com.e3mall.front.common.pojo.Item;
import com.e3mall.front.common.pojo.SearchResult;
import com.e3mall.front.common.utils.E3Result;
import com.e3mall.front.common.utils.JsonUtils;
import com.e3mall.front.domain.TbContent;
import com.e3mall.front.domain.TbItem;
import com.e3mall.front.domain.TbItemDesc;
import com.e3mall.front.utils.CookieUtil;
import com.e3mall.front.utils.CookieUtils;

@Service
public class FrontServiceImpl implements FrontService {

	@Autowired
	private ContentServiceClient contentServiceClient;
	@Autowired
	private SearchServiceClient searchServiceClient;
	@Autowired
	private ItemServiceClient itemServiceClient;
	@Autowired
	private CartServiceClient cartServiceClient;
	@Override
	public List<TbContent> findAllByCid(long lUNBO_ID) {
		return contentServiceClient.findAllByCid(lUNBO_ID);
		
	}
	@Override
	public SearchResult findAllByKeyword(String keyword, int page, int rows) {
		SearchResult result = searchServiceClient.findAllByKeyword(keyword, page, rows);
		return result;
	}

	@Override
	public Map<String, Object> findItemByPid(long itemId) {
		Map<String, Object> map = itemServiceClient.findItemByPid(itemId);
		return map;
	}
	@Override
	public List<TbItem> findCartList() {
		//List<TbItem> cartList = cartServiceClient.findCartList();
		//List<TbItem> cartList  = (List<TbItem>) CookieUtils.getCookie("cart", TbItem.class);
		String cartListJson =  CookieUtils.getCookie("cart", String.class);
		List<TbItem> cartList = JsonUtils.jsonToList(cartListJson, TbItem.class);
		return cartList;
	}
	@Override
	public E3Result deleteCartItem(long itemId) {
		//E3Result deleteCartItem = cartServiceClient.deleteCartItem(itemId);
		E3Result result = new E3Result();
		String cartListJson =  CookieUtils.getCookie("cart", String.class);
		List<TbItem> cartList = JsonUtils.jsonToList(cartListJson, TbItem.class);
		for (TbItem tbItem : cartList) {
			if (itemId == tbItem.getId().longValue()) {
				
				cartList.remove(tbItem);
				
				break;
			}
		}
		return result.ok(cartList);
		
	}



	
}
