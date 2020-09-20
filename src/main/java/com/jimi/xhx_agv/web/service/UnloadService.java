package com.jimi.xhx_agv.web.service;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jimi.xhx_agv.db.SQL;
import com.jimi.xhx_agv.entity.model.Position;
import com.jimi.xhx_agv.util.ResultFactory;
import com.jimi.xhx_agv.util.ResultFactory.Result;
import com.jimi.xhx_agv.web.cache.WorkStateHolder;
import com.jimi.xhx_agv.web.exception.OperationException;
import com.jimi.xhx_agv.web.exception.ParameterException;
import com.jimi.xhx_agv.web.util.AgvCaller;

public class UnloadService {

    public Result listAllPositions() {
    	List<JSONObject> result = new LinkedList<>();
    	List<Position> positions = Position.dao.find(SQL.LIST_ALL_UNLOAD_AREA_POSITIONS);
    	for (Position position : positions) {
			//标记为可用的有：1、位置为装卸类型且无货架且未锁定；2、位置为存储类型且有非空货架且未锁定
    		//其他标记为不可用
    		JSONObject object = new JSONObject();
    		object.put("id", position.getId());
    		object.put("row", position.getRow());
    		object.put("col", position.getCol());
    		object.put("type", position.getType() == 0 ? "unload" : "store");
    		if((position.getType() == 0 && position.getGoodsState() == 0 && position.getIsLock() == false)
    				|| (position.getType() == 1 && position.getGoodsState() == 2 && position.getIsLock() == false)) {
    			object.put("available", true);
    		}else {
    			object.put("available", false);
    		}
    		result.add(object);
		}
		return ResultFactory.succeed(result);
    }
    
    
    public Result callFullShelves(Integer storePosition, Integer unloadPosition) {
    	//判断ulp有效性
    	Position ulp = Position.dao.findById(unloadPosition);
    	if(ulp.getArea() != 1 || ulp.getType() != 0 || ulp.getGoodsState() != 0 || ulp.getIsLock() != false) {
    		throw new ParameterException("参数不是有效的卸载位置");
    	}
    	//判断fp的有效性
    	Position fp = Position.dao.findById(storePosition);
    	if(ulp.getArea() != 1 || ulp.getType() != 1 || ulp.getGoodsState() != 2 || ulp.getIsLock() != false) {
    		throw new OperationException("参数不是有效的非空货架存储位置");
    	}
    	//呼叫
    	AgvCaller.transport(fp, ulp, 2);
    	//更新ep，lp的数据库记录，锁定
    	fp.setIsLock(true);
    	ulp.setIsLock(true);
    	fp.update();
    	ulp.update();
    	//设置工作状态
    	WorkStateHolder.setUnloadAreaWork(true);
    	return ResultFactory.succeed();
    }
    
    
    public Result sendShelvesBack(Integer unloadPosition) {
    	//判断ulp有效性
    	Position ulp = Position.dao.findById(unloadPosition);
    	if(ulp.getArea() != 1 || ulp.getType() != 0 || ulp.getGoodsState() == 0 || ulp.getIsLock() != false) {
    		throw new ParameterException("参数不是有效的卸载位置");
    	}
    	//寻找卸载区空的存储位置
    	Position ep = Position.dao.findFirst(SQL.GET_A_EMPTY_STORE_POSITION_FROM_UNLOAD_AREA);
    	if(ep == null) {
    		throw new OperationException("卸载存储区没有空位");
    	}
    	//呼叫
    	AgvCaller.transport(ulp, ep, 2);
    	//更新ep，lp的数据库记录，锁定
    	ep.setIsLock(true);
    	ulp.setGoodsState(1);//设置有空货架的状态
    	ulp.setIsLock(true);
    	ep.update();
    	ulp.update();
    	//设置工作状态
    	WorkStateHolder.setUnloadAreaWork(true);
    	return ResultFactory.succeed();
    }
    
    
    public Result finish() {
    	//设置工作状态
    	WorkStateHolder.setUnloadAreaWork(false);
		return ResultFactory.succeed();
    }
	
}
