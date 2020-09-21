package com.jimi.xhx_agv.web.controller;

import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.jimi.xhx_agv.web.annotation.Log;
import com.jimi.xhx_agv.web.exception.ParameterException;
import com.jimi.xhx_agv.web.service.UnloadService;

public class UnloadController extends Controller {

	private UnloadService unloadService = Aop.get(UnloadService.class);
	
	
    @Log("获取卸载区所有空卸载位和有货物的存储位")
    public void listAllEmptyUnloadAndHaveGoodsStorePositions() {
    	renderJson(unloadService.listAllEmptyUnloadAndHaveGoodsStorePositions());
    }
    
    
    @Log("呼叫叉车运送位置id为{storePosition}的货架到卸载区:{unloadPosition}")
    public void callFullShelves(Integer storePosition, Integer unloadPosition) {
    	if(storePosition == null || unloadPosition == null) {
    		throw new ParameterException("参数不能为空");
    	}
    	renderJson(unloadService.callFullShelves(storePosition, unloadPosition));
    }
    
    
    @Log("获取所有非空卸载位")
    public void listAllNotEmptyUnloadPositions() {
    	renderJson(unloadService.listAllNotEmptyUnloadPositions());
    }
    
    
    @Log("呼叫叉车运回id为{unloadPosition}的卸载区的货架回二号仓")
    public void sendShelvesBack(Integer unloadPosition) {
    	if(unloadPosition == null) {
    		throw new ParameterException("参数不能为空");
    	}
    	renderJson(unloadService.sendShelvesBack(unloadPosition));
    }
    
    
    @Log("将卸载区工作状态置为结束")
    public void finish() {
    	renderJson(unloadService.finish());
    }
}