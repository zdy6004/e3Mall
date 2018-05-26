package com.e3mall.cart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e3mall.cart.common.jedis.RedisClient;
import com.e3mall.cart.common.utils.CookieUtil;
import com.e3mall.cart.common.utils.CookieUtils;
import com.e3mall.cart.common.utils.E3Result;
import com.e3mall.cart.common.utils.FindUserUtils;
import com.e3mall.cart.common.utils.JsonUtils;
import com.e3mall.cart.domain.TbItem;
import com.e3mall.cart.domain.TbUser;
import com.e3mall.cart.service.CartService;

@RestController
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private RedisClient redisClient;


	@RequestMapping(value = "/add/{itemId}", method = RequestMethod.POST)
	public E3Result addCart(@PathVariable("itemId") long itemId,
			@RequestParam(value = "num", defaultValue = "1") int num, HttpServletResponse response,
			HttpServletRequest request) {
		String cookie = CookieUtils.getCookie("cart");
		System.out.println(cookie);
		TbUser findUser = findUser(request);
		E3Result result = new E3Result();
		if (findUser != null) {

			result = cartService.addCartToRedis(itemId, num, findUser.getId());
			return result.ok();

		}

		List<TbItem> itemListFromCookie = cartService.addCartToCookie(itemId, num, request);
		String objectToJson = JsonUtils.objectToJson(itemListFromCookie);
		CookieUtils.addCookie("cart", objectToJson);
		return result.ok();
	}

	@RequestMapping(value = "/update/num/{itemId}/{num}", method = RequestMethod.POST)
	public E3Result updateNum(@PathVariable("itemId") long itemId, @PathVariable("num") int num, HttpServletRequest request) {
		TbUser findUser = findUser(request);
		if(findUser != null){
			E3Result result = cartService.updateNumToRedis(findUser.getId(), itemId, num);
			return result.ok();
		}
		E3Result result = cartService.updateNum(itemId, num);
		List<TbItem> itemList = (List<TbItem>) result.getData();
		String objectToJson = JsonUtils.objectToJson(itemList);
		CookieUtils.addCookie("cart", objectToJson);
		return result.ok();
	}

	public TbUser findUser(HttpServletRequest request){
		String user_token = CookieUtils.getCookie("user_token", String.class);
			
			
			if (StringUtils.isNotBlank(user_token)) {
				String userJson = (String) redisClient.get("SESSION"+user_token);
				//用户登录过期
				if (StringUtils.isBlank(userJson)) {
					return null;
				}else{
					//用户登录没有过期，获得用户信息
					TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
					return user;
				}
			}
			return null;
		}
}
