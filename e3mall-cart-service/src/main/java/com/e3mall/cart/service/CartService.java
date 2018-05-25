package com.e3mall.cart.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.e3mall.cart.common.pojo.DataGridResult;
import com.e3mall.cart.common.utils.E3Result;
import com.e3mall.cart.domain.TbItem;



public interface CartService {

	List<TbItem> addCart(long itemId, int num, HttpServletRequest request);

	List<TbItem> findCartList(HttpServletRequest request);

	E3Result updateNum(long itemId, int num);

	E3Result deleteCartItem(long itemId, HttpServletRequest request);

	
	
	

}
