package com.jimi.xhx_agv.web.service;

import java.util.Date;

import com.jimi.xhx_agv.entity.model.Position;
import com.jimi.xhx_agv.web.cache.TaskHolder;

public class AgvCallbackService {

	public void agvCallback(String id, String data) {
		//获取位置
		Position sp = Position.dao.findById(data.split("->")[0]);
		Position ep = Position.dao.findById(data.split("->")[1]);
		if(sp == null || ep == null) {
			return;
		}
		//交换货物状态，装货时间，解锁
		int temp = sp.getGoodsState();
		sp.setGoodsState(ep.getGoodsState());
		ep.setGoodsState(temp);
		Date temp2 = sp.getLoadGoodTime();
		sp.setLoadGoodTime(ep.getLoadGoodTime());
		ep.setLoadGoodTime(temp2);
		sp.setIsLock(false);
		ep.setIsLock(false);
		sp.update();
		ep.update();
		//移除任务缓存
		TaskHolder.remove(id);
	}
	
}
