package com.jimi.xhx_agv.thread;

import java.io.FileInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.jimi.xhx_agv.db.DataSourceConfig;
import com.jimi.xhx_agv.web.UndertowBoot;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Main implements SignalHandler {
	
	private boolean killed = false;
	
	private static Logger logger;
	
	private DataSourceConfig dataSourceConfig = new DataSourceConfig();
	private UndertowBoot undertowBoot = new UndertowBoot();
	
	
	public static void main(String[] args) throws Exception {
		//设置logger
		initLogger();
		//开始运行系统
		Main main = new Main();
		//注册Linux信号量
		Signal.handle(new Signal("INT"), main);//对应Ctrl+C
		Signal.handle(new Signal("TERM"), main);//对应kill
		//开启系统
		main.start();
	}


	private static void initLogger() throws Exception {
		Configurator.initialize(null, new ConfigurationSource(new FileInputStream(System.getProperty("user.dir") + "/log4j2.xml")));
		logger = LogManager.getLogger();
	}

	
	private void start() {
		try {
			dataSourceConfig.start();
			undertowBoot.start();
			logger.info("心晓设备服务已成功开启");
		} catch (Throwable e) {
			e.printStackTrace();
			System.err.println("程序异常结束");
			System.exit(1);
		}
	}
	
	
	@Override
	public void handle(Signal arg0) {
		if(killed) {
			System.err.println("程序被强制结束");
			System.exit(1);
		}
		killed = true;
		try {
			System.out.println("正在等待异步日志记录...");
			LogManager.shutdown();
			initLogger();
			logger.info("心晓设备服务已安全关闭");
			dataSourceConfig.shutdown();
			System.out.println("程序正常结束");
			System.exit(0);
		} catch (Throwable e){
			e.printStackTrace();
			System.err.println("程序异常结束");
			System.exit(1);
		}
	}

}

