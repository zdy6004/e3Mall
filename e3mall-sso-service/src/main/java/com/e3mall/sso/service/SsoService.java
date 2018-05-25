package com.e3mall.sso.service;

import com.e3mall.sso.common.utils.E3Result;
import com.e3mall.sso.domain.TbUser;

public interface SsoService {

	E3Result checkParams(String param, int type);

	E3Result register(TbUser user);

	E3Result login(String username, String password);
	
	E3Result findUserByToken(String token);
	

}
