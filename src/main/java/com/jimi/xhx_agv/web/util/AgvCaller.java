package com.jimi.xhx_agv.web.util;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.jimi.xhx_agv.constant.SystemProperties;
import com.jimi.xhx_agv.entity.model.AgvLog;
import com.jimi.xhx_agv.entity.model.Position;
import com.jimi.xhx_agv.util.PropUtil;
import com.jimi.xhx_agv.web.cache.TaskHolder;
import com.jimi.xhx_agv.web.exception.ThirdPartException;
import com.jimi.xhx_agv.web.util.hik.CodeGenerate;
import com.jimi.xhx_agv.web.util.hik.SchedulingTaskSender;

/**
 * AGV的命令处理器 <br>
 * <b>2020年9月19日</b>
 * 
 * @author <a href="https://github.com/darhao">鲁智深</a>
 */
public class AgvCaller {

	public static void transport(Position start, Position end, int priority) throws ThirdPartException {
		try {
			String url = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.HIK_URL);
			String taskType = PropUtil.getString(SystemProperties.FILE_NAME, SystemProperties.HIK_TASK_TYPE);
			String data = start.getId() + "->" + end.getId();
			String id = CodeGenerate.generate();
			String taskRep = SchedulingTaskSender.request(url, id, taskType, start.getHikId(), end.getHikId(), 
					String.valueOf(priority));
			String code = JSON.parseObject(taskRep).getString("code");
			if (!code.equals("0")) {
				String message = JSON.parseObject(taskRep).getString("message");
				throw new ThirdPartException(message);
			}
			//记录任务缓存
			TaskHolder.add(id, data);
			//记录日志
			AgvLog agvLog = new AgvLog();
			agvLog.setTime(new Date());
			agvLog.setMessage("成功下达搬运指令：" + data + "，优先级：" + priority);
			agvLog.save();
		} catch (Exception e) {
			throw new ThirdPartException(e.getMessage());
		}
	}

}
