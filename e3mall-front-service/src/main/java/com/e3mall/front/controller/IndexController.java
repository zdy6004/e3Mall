package com.e3mall.front.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.front.common.pojo.Item;
import com.e3mall.front.common.pojo.SearchResult;
import com.e3mall.front.common.utils.E3Result;
import com.e3mall.front.common.utils.JsonUtils;
import com.e3mall.front.domain.TbContent;
import com.e3mall.front.domain.TbItem;
import com.e3mall.front.domain.TbItemDesc;
import com.e3mall.front.service.FrontService;
import com.e3mall.front.utils.CookieUtil;
import com.e3mall.front.utils.CookieUtils;

@Controller
public class IndexController {

	@Value("${LUNBO.LUNBO_ID}")
	private long LUNBO_ID;
	@Value("${SEARCH.ROW_NUMBER}")
	private int ROW_NUMBER;
	@Autowired
	private FrontService frontService;
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	
	public String showIndex(Model model){
		List<TbContent> contentList = frontService.findAllByCid(LUNBO_ID);
		model.addAttribute("ad1List", contentList);
		return "index";
	}
	
	
	@RequestMapping("/search")
	public String search(String keyword, @RequestParam(defaultValue = "1") int page, Model model){
		SearchResult result = frontService.findAllByKeyword(keyword, page, ROW_NUMBER);
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", result.getRecordCount());
		model.addAttribute("itemList", result.getItemList());
		return "search";
	}
	
	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET)
	public String showItem(@PathVariable("itemId") long itemId, Model model){
		Map<String, Object> map = frontService.findItemByPid(itemId);
//		String ha = (String) map.get("ha");
//		System.out.println(ha);
//		List<TbContent> findAll = (List<TbContent>) map.get("findAll");
//		System.out.println(findAll);
		String itemDescToJson = (String) map.get("itemDesc");
		String tbItemToJson = (String) map.get("tbItem");
		TbItem tbItem = JsonUtils.jsonToPojo(tbItemToJson, TbItem.class);
		TbItemDesc itemDesc = JsonUtils.jsonToPojo(itemDescToJson, TbItemDesc.class);
		Item item = new Item(tbItem);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
		
	}
	
	@RequestMapping("/page/login")
	public String showLoginPage(){
		return "login";
	}
	
	@RequestMapping("/page/register")
	public String showRegisterPage(){
		return "register";
	}
	
	@RequestMapping(value = "/cartSuccess", method = RequestMethod.GET)
	public String showCartSuccess(){
		
		return "cartSuccess";
	}
	@RequestMapping(value = "/cart/cart", method = RequestMethod.GET)
	public String showCart(Model model,HttpServletRequest request){

		List<TbItem> cartList =  frontService.findCartList();
		
	
		long totalPrice = 0;
		for (TbItem tbItem : cartList) {
			long price = tbItem.getNum() * tbItem.getPrice();
			String image = tbItem.getImage();
			String[] images = image.split(",");
			tbItem.setImage(images[0]);
			totalPrice += price;
		}
		model.addAttribute("cartList", cartList);
		model.addAttribute("totalPrice", totalPrice);
		return "cart";
	}
	@RequestMapping(value = "/cart/delete/{itemId}", method = RequestMethod.GET)
	public String deleteCartItem(@PathVariable("itemId") long itemId, Model model) {
		E3Result result = frontService.deleteCartItem(itemId);
		
		List<TbItem> cartList = (List<TbItem>) result.getData();
		long totalPrice = 0;
		for (TbItem tbItem : cartList) {
			long price = tbItem.getNum() * tbItem.getPrice();
			String image = tbItem.getImage();
			String[] images = image.split(",");
			tbItem.setImage(images[0]);
			totalPrice += price;
		}

		String objectToJson = JsonUtils.objectToJson(cartList);
		CookieUtils.addCookie("cart", objectToJson);
		return "redirect:/front/cart/cart";
	}
}
