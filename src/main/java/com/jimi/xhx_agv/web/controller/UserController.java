package com.jimi.xhx_agv.web.controller;

import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.jimi.xhx_agv.web.annotation.Log;
import com.jimi.xhx_agv.web.exception.ParameterException;
import com.jimi.xhx_agv.web.service.UserService;

public class UserController extends Controller {

	private UserService userService = Aop.get(UserService.class);
	
	
	@Log("{user}登录")
	public void login(String user, String password) {
		if(user == null || password == null) {
			throw new ParameterException("参数不能为空");
		}
		renderJson(userService.login(user, password));
	}
	
}
