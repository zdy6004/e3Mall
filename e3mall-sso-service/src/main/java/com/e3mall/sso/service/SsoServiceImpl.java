package com.e3mall.sso.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import com.e3mall.sso.common.jedis.RedisClient;
import com.e3mall.sso.common.utils.CookieUtils;
import com.e3mall.sso.common.utils.E3Result;
import com.e3mall.sso.common.utils.JsonUtils;
import com.e3mall.sso.domain.TbUser;
import com.e3mall.sso.repository.UserRepository;
import com.netflix.client.http.HttpResponse;

@Service
public class SsoServiceImpl implements SsoService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RedisClient redisClient;
	@Value("${SESSION.EXPIRE_TIME}")
	private long EXPIRE_TIME;
	@Override
	public E3Result checkParams(String param, int type) {
		E3Result result = new E3Result();
		Boolean isExist = false;
		// type = 1, 验证用户名
		// type = 2, 验证手机
		if (type == 1) {

			List<TbUser> users = userRepository.findAllByUsername(param);
			if (users != null && users.size() > 0) {

				return result.build(500, "用户名已存在", isExist);
			} else {
				return result.ok(!isExist);
			}

		}
		if (type == 2) {
			List<TbUser> users = userRepository.findAllByPhone(param);
			if (users != null && users.size() > 0) {
				return result.build(500, "该电话号码已被使用", isExist);
			} else {
				return result.ok(!isExist);
			}

		}
		return null;
	}

	@Override
	public E3Result register(TbUser user) {
		E3Result result = new E3Result();
		user.setUpdated(new Date());
		user.setCreated(new Date());
		userRepository.save(user);
		return result.ok();
	}

	@Override
	public E3Result login(String username, String password) {
		E3Result result = new E3Result();
		TbUser user = userRepository.findOneByUsername(username);
		if (user != null) {
			if (password.equals(user.getPassword())) {
				String token = UUID.randomUUID().toString();
				user.setPassword(null);
				redisClient.set("SESSION"+token, user, EXPIRE_TIME);
				return result.ok(token);
			}
		}
		return result.build(500, "用户名或密码错误");
	}

	@Override
	public E3Result findUserByToken(String token) {
		E3Result result = new E3Result();
		TbUser user = (TbUser) redisClient.get("SESSION"+token);
		if(user == null){
			return result.build(500, "session过期");
		}
		return result.ok(user);
	}

}
