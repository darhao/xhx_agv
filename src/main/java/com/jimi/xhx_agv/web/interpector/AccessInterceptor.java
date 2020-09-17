package com.jimi.xhx_agv.web.interpector;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jimi.xhx_agv.util.TokenBox;
import com.jimi.xhx_agv.web.exception.AccessException;
import com.jimi.xhx_agv.web.exception.ParameterException;

import javax.servlet.http.HttpServletRequest;

public class AccessInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        //接口权限限制
        String servletPath = inv.getController().getRequest().getServletPath();
        if (servletPath.equals("/user/login")){
            inv.invoke();
            return;
        }

        HttpServletRequest req = inv.getController().getRequest();
        String token = req.getParameter(TokenBox.TOKEN_ID_KEY_NAME);
        if (token == null){
            throw new ParameterException("Token不能为空");
        }
        //根据token获取用户id
        Integer userId = TokenBox.get(token, "userId");
        if (userId == null){
            throw new AccessException("请先登录");
        }

        inv.invoke();
    }



}
