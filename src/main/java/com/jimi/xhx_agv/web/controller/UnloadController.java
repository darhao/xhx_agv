package com.jimi.xhx_agv.web.controller;

import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.jimi.xhx_agv.web.annotation.Log;
import com.jimi.xhx_agv.web.service.UnloadService;

public class UnloadController extends Controller {

	private UnloadService unloadService = Aop.get(UnloadService.class);
	
	
    @Log("获取卸载区所有位置")
    public void listAllPositions() {
    	
    }
    
    
    @Log("呼叫叉车运送位置id为{storePosition}的货架到卸载区:{unloadPosition}")
    public void callFullShelves(Integer storePosition, Integer unloadPosition) {
    	
    }
    
    
    @Log("呼叫叉车运回id为{unloadPosition}的卸载区的货架回二号仓")
    public void sendShelvesBack(Integer unloadPosition) {
    	
    }
    
    
    @Log("将卸载区工作状态置为结束")
    public void finish() {
    	
    }
}