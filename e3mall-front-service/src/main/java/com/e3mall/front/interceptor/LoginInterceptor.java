package com.e3mall.front.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.e3mall.front.common.jedis.RedisClient;
import com.e3mall.front.common.utils.JsonUtils;
import com.e3mall.front.domain.TbUser;
import com.e3mall.front.utils.CookieUtils;


public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	private RedisClient redisClient;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		System.out.println("登录拦截~~~~~~~~~~~~~~~~~~");
//		// cookie中获取用户信息
//		String user_token = CookieUtils.getCookie("user_token", String.class);
//		
//		
//		if (StringUtils.isNotBlank(user_token)) {
//			String userJson = (String) redisClient.get("SESSION"+user_token);
//			//用户登录过期
//			if (StringUtils.isBlank(userJson)) {
//				return true;
//			}else{
//				//用户登录没有过期，获得用户信息
//				TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
//				request.setAttribute("user", user);
//				return true;
//			}
//			
//		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
