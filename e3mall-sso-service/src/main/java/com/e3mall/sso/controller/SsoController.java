package com.e3mall.sso.controller;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.e3mall.sso.common.utils.CookieUtil;
import com.e3mall.sso.common.utils.CookieUtils;
import com.e3mall.sso.common.utils.E3Result;
import com.e3mall.sso.common.utils.JsonUtils;
import com.e3mall.sso.domain.TbUser;
import com.e3mall.sso.service.SsoService;


@RestController
public class SsoController {
	@Autowired
	private SsoService ssoService;

	
	@RequestMapping("/check/{param}/{type}")
	public E3Result checkParams(@PathVariable("param") String param, 
			@PathVariable("type") int type){
		E3Result result = ssoService.checkParams(param, type);
	
		return result;
	}
	@RequestMapping("/register")
	public E3Result register(TbUser user){
		E3Result result = ssoService.register(user);
		return result;
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response){
		E3Result result = ssoService.login(username, password);
		String token =  (String) result.getData();
		if(StringUtils.isNotBlank(token)){
			CookieUtil.addCookie(response, "user_token", token, 600);
		}
		
		return result;
	}
	
	//json跨域 解决方法一
//	@RequestMapping(value = "/token/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public String findUserByToken(@PathVariable("token") String token, String callback){
//		E3Result result = ssoService.findUserByToken(token);
//		if(StringUtils.isNotBlank(callback)){
//			return callback + "(" + JsonUtils.objectToJson(result) + ");";
//		}
//		return JsonUtils.objectToJson(result);
//	}
	
	//json跨域 解决方法二
	@RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
	public Object findUserByToken(@PathVariable("token") String token, String callback){
		E3Result result = ssoService.findUserByToken(token);
		if(StringUtils.isNotBlank(callback)){
			MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
			jacksonValue.setJsonpFunction(callback);
			return jacksonValue;
		}
		return result;
	}
	
}
