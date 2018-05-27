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
import com.e3mall.cart.domain.TbUser;
import com.e3mall.cart.repository.ItemDescReposiroty;
import com.e3mall.cart.repository.ItemReposiroty;
import com.e3mall.cart.repository.contentRepository;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ItemServiceClient itemServiceClient;
	@Autowired
	private RedisClient redisClient;

	@Override
	public List<TbItem> addCartToCookie(long itemId, int num, HttpServletRequest request) {
		// cookie中获取购物车列表
		List<TbItem> cartList = getItemListFromCookie();
		
		for (TbItem tbItem : cartList) {
			if (itemId == tbItem.getId().longValue()) {
				tbItem.setNum(num + tbItem.getNum());
				return cartList;
			}
		}

		Map<String, Object> map = itemServiceClient.findItemByPid(itemId);
		String tbItemToJson = (String) map.get("tbItem");
		TbItem tbItem = JsonUtils.jsonToPojo(tbItemToJson, TbItem.class);
		tbItem.setNum(num);
		String images = tbItem.getImage();
		String[] split = images.split(",");
		tbItem.setImage(split[0]);
		cartList.add(tbItem);
		

		return cartList;

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
	public E3Result addCartToRedis(long itemId, int num, long userId) {
		boolean exists = redisClient.hmExists("Cart"+userId, itemId+"");
		
		if(exists){
			String cartListJson = (String) redisClient.hmGet("Cart"+userId, itemId+"");
			TbItem item = JsonUtils.jsonToPojo(cartListJson, TbItem.class);
			item.setNum(item.getNum() + num);
			redisClient.hmSet("Cart"+userId, itemId+"", JsonUtils.objectToJson(item));
			return E3Result.ok();
		}else{
			Map<String, Object> findItemByPid = itemServiceClient.findItemByPid(itemId);
			String itemJson = (String) findItemByPid.get("tbItem");
			TbItem tbItem = JsonUtils.jsonToPojo(itemJson, TbItem.class);
			tbItem.setNum(num);
			String images= tbItem.getImage();
			if(StringUtils.isNotBlank(images)){
				String[] image = images.split(",");
				tbItem.setImage(image[0]);
			}
			
			redisClient.hmSet("Cart"+userId, itemId+"", JsonUtils.objectToJson(tbItem));
			return E3Result.ok();
		}
		
	}

	@Override
	public E3Result updateNumToRedis(long userId, long itemId, int num) {
		String cartListJson = (String) redisClient.hmGet("Cart"+userId, itemId+"");
		TbItem item = JsonUtils.jsonToPojo(cartListJson, TbItem.class);
		item.setNum(num);
		redisClient.hmSet("Cart"+userId, itemId+"", JsonUtils.objectToJson(item));
		return E3Result.ok();
	}

	@Override
	public E3Result clearCartList(long userId) {
		redisClient.remove("Cart"+userId);
		return E3Result.ok();
		
	}

	

}