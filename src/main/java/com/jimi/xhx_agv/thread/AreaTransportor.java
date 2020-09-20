package com.jimi.xhx_agv.thread;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jimi.xhx_agv.db.SQL;
import com.jimi.xhx_agv.entity.model.Position;
import com.jimi.xhx_agv.util.ExceptionLogger;
import com.jimi.xhx_agv.web.cache.WorkStateHolder;
import com.jimi.xhx_agv.web.exception.ThirdPartException;
import com.jimi.xhx_agv.web.interpector.LockInterceptor;
import com.jimi.xhx_agv.web.util.AgvCaller;

/**
 * 仓库之间转运线程
 * <br>
 * <b>2019年10月10日</b>
 * @author <a href="https://github.com/darhao">鲁智深</a>
 */
public class AreaTransportor implements UncaughtExceptionHandler{
	
	private static final Logger logger = LogManager.getLogger();
	
	private ScheduledExecutorService transportor;
	
	
	public void start() {
		final int CYCLE = 30;
		transportor = Executors.newSingleThreadScheduledExecutor((runnable)->{
			Thread thread = new Thread(runnable);
			thread.setName("货架转运线程");
			thread.setUncaughtExceptionHandler(this);
			return thread;
		});
		transportor.scheduleWithFixedDelay(()->{
			synchronized (LockInterceptor.getLock()) {
				transport();
			}
		}, CYCLE, CYCLE, TimeUnit.SECONDS);
	}


	private void transport() {
		//如果任一个仓库处于工作状态则直接返回
		if(WorkStateHolder.isLoadAreaWork() || WorkStateHolder.isUnloadAreaWork()) {
			return;
		}
		//获取所有装载区转载时间超过2小时货物的位置tps
		List<Position> coldDownGoodsPositions = Position.dao.find(SQL.GET_ALL_COLD_DOWN_GOODS_POSITION);
		for (Position cdp : coldDownGoodsPositions) {
			//获取一个卸载区的存储空位
			Position ep = Position.dao.findFirst(SQL.GET_A_EMPTY_STORE_POSITION_FROM_UNLOAD_AREA);
			if(ep == null) {
				break;
			}
			try {
				//呼叫
				AgvCaller.transport(cdp, ep, 1);
				//锁定
				cdp.setIsLock(true);
				ep.setIsLock(true);
				cdp.update();
				ep.update();
			}catch (ThirdPartException e) {
				logger.error(e);
			}
		}
		//获取所有卸载区空货架存储位置
		List<Position> emptyShelvesPositions = Position.dao.find(SQL.GET_ALL_EMPTY_SHELVES_STORE_POSITION_FROM_UNLOAD_AREA);
		for (Position esp : emptyShelvesPositions) {
			//获取一个装载区的存储空位
			Position ep = Position.dao.findFirst(SQL.GET_A_EMPTY_STORE_POSITION_FROM_LOAD_AREA);
			if(ep == null) {
				break;
			}
			try {
				//呼叫
				AgvCaller.transport(esp, ep, 1);
				//锁定
				esp.setIsLock(true);
				ep.setIsLock(true);
				esp.update();
				ep.update();
			}catch (ThirdPartException e) {
				logger.error(e);
			}
		}
	}
	
	
	public void shutdown() throws InterruptedException {
		transportor.shutdown();
		transportor.awaitTermination(1, TimeUnit.DAYS);
	}


	@Override
	public void uncaughtException(Thread t, Throwable e) {
		ExceptionLogger.logError(logger, e);
	}
	
}
