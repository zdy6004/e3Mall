package com.e3mall.cart.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.e3mall.cart.client.ItemServiceClient;
import com.e3mall.cart.common.jedis.RedisClient;
import com.e3mall.cart.common.pojo.DataGridResult;
import com.e3mall.cart.common.utils.CookieUtil;
import com.e3mall.cart.common.utils.CookieUtils;
import com.e3mall.cart.common.utils.E3Result;
import com.e3mall.cart.common.utils.IDUtils;
import com.e3mall.cart.common.utils.JsonUtils;
import com.e3mall.cart.domain.TbContent;
import com.e3mall.cart.domain.TbItem;
import com.e3mall.cart.domain.TbItemDesc;
import com.e3mall.cart.repository.ItemDescReposiroty;
import com.e3mall.cart.repository.ItemReposiroty;
import com.e3mall.cart.repository.contentRepository;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ItemServiceClient itemServiceClient;

	@Override
	public List<TbItem> addCart(long itemId, int num, HttpServletRequest request) {
		List<TbItem> itemListFromCookie = getItemListFromCookie();
		System.out.println("添加前"+itemListFromCookie.size());
		for (TbItem tbItem : itemListFromCookie) {
			if (itemId == tbItem.getId().longValue()) {
				tbItem.setNum(num + tbItem.getNum());
				return itemListFromCookie;
			}
		}

		Map<String, Object> map = itemServiceClient.findItemByPid(itemId);
		String tbItemToJson = (String) map.get("tbItem");
		TbItem tbItem = JsonUtils.jsonToPojo(tbItemToJson, TbItem.class);
		tbItem.setNum(num);
		String images = tbItem.getImage();
		String[] split = images.split(",");
		tbItem.setImage(split[0]);
		itemListFromCookie.add(tbItem);
		for (TbItem it : itemListFromCookie) {
			System.out.println(it);
		}

		return itemListFromCookie;

	}

	public List<TbItem> getItemListFromCookie() {
		String cartListJson = CookieUtils.getCookie("cart", String.class);
		
		if (StringUtils.isBlank(cartListJson)) {
			return new ArrayList<>();
			
		}
		List<TbItem> itemList = JsonUtils.jsonToList(cartListJson, TbItem.class);
		

			return itemList;
		

		
		
	}
	

	@Override
	public List<TbItem> findCartList(HttpServletRequest request) {
		String header = request.getHeader("Cookie");
		System.out.println(header);
		List<TbItem> itemListFromCookie = JsonUtils.jsonToList(header, TbItem.class);
		return itemListFromCookie;
	}

	@Override
	public E3Result updateNum(long itemId, int num) {
		E3Result result = new E3Result();
		List<TbItem> itemListFromCookie = getItemListFromCookie();
		for (TbItem tbItem : itemListFromCookie) {
			if (itemId == tbItem.getId().longValue()) {
				tbItem.setNum(num);
				break;
			}
		}
		return result.ok(itemListFromCookie);
	}

	@Override
	public E3Result deleteCartItem(long itemId, HttpServletRequest request) {
		E3Result result = new E3Result();
		List<TbItem> itemListFromCookie = getItemListFromCookie();
		System.out.println("删除之前"+itemListFromCookie.size());
		for (TbItem tbItem : itemListFromCookie) {
			if (itemId == tbItem.getId().longValue()) {
				
				itemListFromCookie.remove(tbItem);
				
				break;
			}
		}
		System.out.println("删除之后"+itemListFromCookie.size());
		return result.ok(itemListFromCookie);
	}

}