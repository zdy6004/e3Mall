package com.e3mall.cart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.e3mall.cart.common.utils.CookieUtil;
import com.e3mall.cart.common.utils.CookieUtils;
import com.e3mall.cart.common.utils.E3Result;
import com.e3mall.cart.common.utils.JsonUtils;
import com.e3mall.cart.domain.TbItem;
import com.e3mall.cart.service.CartService;


@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value = "/add/{itemId}", method = RequestMethod.POST)
	public E3Result addCart(@PathVariable("itemId") long itemId, @RequestParam(value = "num", defaultValue = "1") int num, HttpServletResponse response, HttpServletRequest request){
		E3Result result = new E3Result();
		List<TbItem> itemListFromCookie = cartService.addCart(itemId, num,request);
		String objectToJson = JsonUtils.objectToJson(itemListFromCookie);
		CookieUtils.addCookie("cart", objectToJson);
		return result.ok();
	}
	
//	@RequestMapping(value = "/front/cart/cart", method = RequestMethod.GET)
//	public List<TbItem> findCartList(HttpServletRequest request){
//		System.out.println("111");
//		Cookie cookie = CookieUtil.getCookieByName(request, "cart");
//		String value = cookie.getValue();
//		System.out.println(value);
//		return null;
//	}
	
	@RequestMapping(value = "/update/num/{itemId}/{num}", method = RequestMethod.POST)
	public E3Result updateNum(@PathVariable("itemId") long itemId, @PathVariable("num") int num){
		E3Result result = cartService.updateNum(itemId, num);
		List<TbItem> itemList = (List<TbItem>) result.getData();
		String objectToJson = JsonUtils.objectToJson(itemList);
		CookieUtils.addCookie("cart", objectToJson);
		return result.ok();
	} 
	@RequestMapping(value = "/front/cart/delete/{itemId}", method = RequestMethod.GET)
	public E3Result deleteCartItem(@PathVariable("itemId") long itemId, HttpServletResponse response, HttpServletRequest request){
		E3Result result = cartService.deleteCartItem(itemId,request);
		List<TbItem> itemList = (List<TbItem>) result.getData();
		String objectToJson = JsonUtils.objectToJson(itemList);
		System.out.println(objectToJson);
		CookieUtils.addCookie("cart", objectToJson);
		return result.ok(itemList);
	}
}
