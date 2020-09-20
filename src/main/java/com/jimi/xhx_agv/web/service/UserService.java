package com.jimi.xhx_agv.web.service;

import com.jimi.xhx_agv.db.SQL;
import com.jimi.xhx_agv.entity.model.User;
import com.jimi.xhx_agv.util.ResultFactory;
import com.jimi.xhx_agv.util.ResultFactory.Result;
import com.jimi.xhx_agv.util.TokenBox;
import com.jimi.xhx_agv.web.exception.OperationException;

public class UserService {

	public Result login(String user, String password) {
		User u = User.dao.findFirst(SQL.GET_USER_BY_NAME_AND_PASSWOR, user, password);
		if(u == null) {
			throw new OperationException("用户名或密码错误");
		}
		TokenBox.put(TokenBox.createTokenId(), "userId", u.getId());
		return ResultFactory.succeed();
	}
	
}
