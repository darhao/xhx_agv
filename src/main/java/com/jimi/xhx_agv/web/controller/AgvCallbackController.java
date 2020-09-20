package com.jimi.xhx_agv.web.controller;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.jimi.xhx_agv.entity.model.AgvLog;
import com.jimi.xhx_agv.util.ExceptionLogger;
import com.jimi.xhx_agv.web.annotation.Log;
import com.jimi.xhx_agv.web.cache.TaskHolder;
import com.jimi.xhx_agv.web.service.AgvCallbackService;

public class AgvCallbackController extends Controller {

	private AgvCallbackService agvCallbackService = Aop.get(AgvCallbackService.class);
	
	private static final Logger logger = LogManager.getLogger();
	
	
	@Log("收到agv任务通知")
	public void agvCallback() {
		//反序列化
		JSONObject callbackJson = JSON.parseObject(getRawData());
		String method = callbackJson.getString("method");
		String id = callbackJson.getString("reqCode");
		String data = TaskHolder.get(id);
		if(data != null) {
			//记录日志
			AgvLog agvLog = new AgvLog();
			agvLog.setTime(new Date());
			agvLog.setMessage("任务进度通知：" + method + "，" + data);
			agvLog.save();
			//处理结束的任务
			if(method.equals("end")) {
				try {
					agvCallbackService.agvCallback(id, data);
				} catch (Exception e) {
					ExceptionLogger.logError(logger, e);
				}
			}
		}
		//响应
		JSONObject resp = new JSONObject();
		resp.put("code", "0");
		resp.put("message", "成功");
		resp.put("reqCode", id);
		resp.put("data", "");
		renderJson(resp);
	}
	
}
