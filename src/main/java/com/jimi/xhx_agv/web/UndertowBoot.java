package com.jimi.xhx_agv.web;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.jimi.xhx_agv.constant.SystemProperties;
import com.jimi.xhx_agv.util.PropUtil;
import com.jimi.xhx_agv.util.TokenBox;
import com.jimi.xhx_agv.web.controller.AgvCallbackController;
import com.jimi.xhx_agv.web.controller.LoadController;
import com.jimi.xhx_agv.web.controller.UnloadController;
import com.jimi.xhx_agv.web.controller.UserController;
import com.jimi.xhx_agv.web.interpector.AccessInterceptor;
import com.jimi.xhx_agv.web.interpector.ActionLogInterceptor;
import com.jimi.xhx_agv.web.interpector.CORSInterceptor;
import com.jimi.xhx_agv.web.interpector.ErrorLogInterceptor;
import com.jimi.xhx_agv.web.interpector.LockInterceptor;

public class UndertowBoot extends JFinalConfig {
 
	private UndertowServer undertowServer;
	
	
	public void start() {
		//配置web服务器
		undertowServer = UndertowServer.create(UndertowBoot.class);
		int port = PropUtil.getInt(SystemProperties.FILE_NAME, SystemProperties.UNDERTOW_LISTEN_PORT);
		undertowServer.setPort(port);
		undertowServer.start();
		TokenBox.start(0);
	}
	
	
	public void configRoute(Routes me) {
		me.add("user", UserController.class);
		me.add("unload", UnloadController.class);
		me.add("load", LoadController.class);
		me.add("agv", AgvCallbackController.class);
    }
	
	
    public void configInterceptor(Interceptors me) {
    	me.addGlobalActionInterceptor(new ErrorLogInterceptor());
        me.addGlobalActionInterceptor(new CORSInterceptor());
        me.addGlobalActionInterceptor(new AccessInterceptor());
        me.addGlobalActionInterceptor(new ActionLogInterceptor());
        me.addGlobalServiceInterceptor(new Tx());
        me.addGlobalServiceInterceptor(new LockInterceptor());
    }
    
    
    public void configConstant(Constants me) {
    	me.setJsonFactory(new MixedJsonFactory());
    	me.setToCglibProxyFactory();
    }
    
    
    public void configEngine(Engine me) {}
    public void configPlugin(Plugins me) {}
    public void configHandler(Handlers me) {}
}
