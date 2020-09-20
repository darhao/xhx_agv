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

public class LoadService {

    public Result listAllLoadPositions() {
    	List<JSONObject> result = new LinkedList<>();
    	List<Position> positions = Position.dao.find(SQL.LIST_ALL_LOAD_POSITIONS);
    	for (Position position : positions) {
			//无货架且未锁定的标记为可用，其他标记为不可用
    		JSONObject object = new JSONObject();
    		object.put("id", position.getId());
    		if(position.getGoodsState() == 0 && position.getIsLock() == false) {
    			object.put("available", true);
    		}else {
    			object.put("available", false);
    		}
    		result.add(object);
		}
		return ResultFactory.succeed(result);
    }
    
    
    public Result callEmptyShelves(Integer loadPosition) {
    	//判断lp有效性
    	Position lp = Position.dao.findById(loadPosition);
    	if(lp.getArea() != 0 || lp.getType() != 0 || lp.getGoodsState() != 0 || lp.getIsLock() != false) {
    		throw new ParameterException("参数不是有效的装载位置");
    	}
    	//寻找装载区空货架存储位置
    	Position ep = Position.dao.findFirst(SQL.GET_A_EMPTY_SHELVES_STORE_POSITION_FROM_LOAD_AREA);
    	if(ep == null) {
    		throw new OperationException("装载存储区没有空货架");
    	}
    	//呼叫
    	AgvCaller.transport(ep, lp, 2);
    	//更新ep，lp的数据库记录，锁定
    	ep.setIsLock(true);
    	lp.setIsLock(true);
    	ep.update();
    	lp.update();
    	//设置工作状态
    	WorkStateHolder.setLoadAreaWork(true);
    	return ResultFactory.succeed();
    }
    
    
    public Result sendShelvesBack(Integer loadPosition) {
    	//判断lp有效性
    	Position lp = Position.dao.findById(loadPosition);
    	if(lp.getArea() != 0 || lp.getType() != 0 || lp.getGoodsState() == 0 || lp.getIsLock() != false) {
    		throw new ParameterException("参数不是有效的装载位置");
    	}
    	//寻找装载区空的存储位置
    	Position ep = Position.dao.findFirst(SQL.GET_A_EMPTY_STORE_POSITION_FROM_LOAD_AREA);
    	if(ep == null) {
    		throw new OperationException("装载存储区没有空位");
    	}
    	//呼叫
    	AgvCaller.transport(lp, ep, 2);
    	//更新ep，lp的数据库记录，锁定
    	ep.setIsLock(true);
    	lp.setGoodsState(2);//设置有非空货架的状态
    	lp.setIsLock(true);
    	ep.update();
    	lp.update();
    	//设置工作状态
    	WorkStateHolder.setLoadAreaWork(true);
    	return ResultFactory.succeed();
    }
    
    
    public Result finish() {
    	//设置工作状态
    	WorkStateHolder.setLoadAreaWork(false);
		return ResultFactory.succeed();
    }
}
