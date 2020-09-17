package com.jimi.xhx_agv.web.controller;

import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.jimi.xhx_agv.web.annotation.Log;
import com.jimi.xhx_agv.web.service.LoadService;

public class LoadController extends Controller {

    private LoadService loadService = Aop.get(LoadService.class);

    
    @Log("获取所有装载位")
    public void listAllLoadPositions() {
    	
    }
    
    
    @Log("呼叫叉车运送空货架到装载区:{loadPosition}")
    public void callEmptyShelves(Integer loadPosition) {
    	
    }
    
    
    @Log("呼叫叉车运回id为{loadPosition}的装载区的货架回一号仓")
    public void sendShelvesBack(Integer loadPosition) {
    	
    }
    
    
    @Log("将装载区工作状态置为结束")
    public void finish() {
    	
    }
}
