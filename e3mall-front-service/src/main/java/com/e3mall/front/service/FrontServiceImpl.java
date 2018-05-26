package com.e3mall.front.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.front.client.CartServiceClient;
import com.e3mall.front.client.ContentServiceClient;
import com.e3mall.front.client.ItemServiceClient;
import com.e3mall.front.client.SearchServiceClient;
import com.e3mall.front.common.jedis.RedisClient;
import com.e3mall.front.common.pojo.SearchResult;
import com.e3mall.front.common.utils.E3Result;
import com.e3mall.front.common.utils.JsonUtils;
import com.e3mall.front.domain.TbContent;
import com.e3mall.front.domain.TbItem;
import com.e3mall.front.domain.TbUser;
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
	@Autowired
	private RedisClient redisClient;

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
	public List<TbItem> findCartList(HttpServletRequest request) {
		String cartListJson = CookieUtils.getCookie("cart", String.class);
		List<TbItem> cartList = new ArrayList<>();
		if(StringUtils.isNotBlank(cartListJson)){
			cartList = JsonUtils.jsonToList(cartListJson, TbItem.class);
		}
		TbUser findUser = findUser(request);
		if (findUser != null) {
			mergeCartlist(findUser.getId(), cartList);
			CookieUtils.removeCookie("cart");
			cartList = getCartListFromRedis(findUser.getId());
		}

		return cartList;
	}

	@Override
	public E3Result deleteCartItem(long itemId) {
		
		E3Result result = new E3Result();
		String cartListJson = CookieUtils.getCookie("cart", String.class);
		List<TbItem> cartList = JsonUtils.jsonToList(cartListJson, TbItem.class);
		for (TbItem tbItem : cartList) {
			if (itemId == tbItem.getId().longValue()) {

				cartList.remove(tbItem);

				break;
			}
		}
		return result.ok(cartList);

	}

	public TbUser findUser(HttpServletRequest request) {
		String user_token = CookieUtils.getCookie("user_token", String.class);

		if (StringUtils.isNotBlank(user_token)) {
			String userJson = (String) redisClient.get("SESSION" + user_token);
			// 用户登录过期
			if (StringUtils.isBlank(userJson)) {
				return null;
			} else {
				// 用户登录没有过期，获得用户信息
				TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
				return user;
			}
		}
		return null;
	}
	
	public E3Result mergeCartlist(long userId, List<TbItem> cartList){
		if(cartList != null && cartList.size() >1){
			for (TbItem tbItem : cartList) {
				addCartList(tbItem.getId(), tbItem.getNum(), userId);
			}
		}
		
		return E3Result.ok();
	}

	public E3Result addCartList(long itemId, int num, long userId) {
		boolean exists = redisClient.hmExists("Cart" + userId, itemId + "");

		if (exists) {
			String cartListJson = (String) redisClient.hmGet("Cart" + userId, itemId + "");
			TbItem item = JsonUtils.jsonToPojo(cartListJson, TbItem.class);
			item.setNum(item.getNum() + num);
			redisClient.hmSet("Cart" + userId, itemId + "", JsonUtils.objectToJson(item));
			return E3Result.ok();
		} else {
			Map<String, Object> findItemByPid = itemServiceClient.findItemByPid(itemId);
			String itemJson = (String) findItemByPid.get("tbItem");
			TbItem tbItem = JsonUtils.jsonToPojo(itemJson, TbItem.class);
			tbItem.setNum(num);
			String images = tbItem.getImage();
			if (StringUtils.isNotBlank(images)) {
				String[] image = images.split(",");
				tbItem.setImage(image[0]);
			}

			redisClient.hmSet("Cart" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
			return E3Result.ok();
		}
		

	}
	public List<TbItem> getCartListFromRedis(long userId){
		List<Object> hmGetHvals = redisClient.hmGetHvals("Cart" + userId);
		List<TbItem> cartList = new ArrayList<>();
		for (Object object : hmGetHvals) {
			TbItem tbItem = JsonUtils.jsonToPojo((String)object, TbItem.class);
			cartList.add(tbItem);
		}
		return cartList;
	}

	@Override
	public E3Result deleteCartItemToRedis(long userId, long itemId) {
		redisClient.hmRemove("Cart"+userId, itemId+"");
		List<TbItem> cartListFromRedis = getCartListFromRedis(userId);
		return E3Result.ok(cartListFromRedis);
	}

}
