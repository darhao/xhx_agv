package com.jimi.xhx_agv.web.interpector;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 统一加锁拦截器
 * <br>
 * <b>2020年9月17日</b>
 * @author <a href="https://github.com/darhao">鲁智深</a>
 */
public class LockInterceptor implements Interceptor {

	private static final Object SHERLOCK = new Object();
	
	
    @Override
    public void intercept(Invocation inv) {
        synchronized (SHERLOCK) {
        	inv.invoke();
		}
    }

}
