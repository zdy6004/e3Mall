package com.e3mall.order.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.e3mall.order.common.jedis.RedisClient;
import com.e3mall.order.domain.TbUser;

@Component
public class FindUserUtils {
	@Autowired
	private RedisClient redisClient;
	
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
