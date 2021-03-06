package com.jimi.xhx_agv.web.util.hik;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;


/**
 * 该类用于生成AGV调度任务单并发送到服务器
 * 该类所有方法的参数，没有特别说明的情况下，都不能为空，否则会抛出{@link NullPointerException}<br>
 * 线程安全级别：安全
 * Date: Created in 2019/6/26 11:59
 * Author: ct
 */
public class SchedulingTaskSender {

  /**
   * 向服务器发送生成AGV调度任务单请求
   * @param url  访问RCS-2000的完整路径
   * @param taskTyp 任务类型 可以为null
   * @param start  起始点，如：位置点a01
   * @param end 终点，如：位置点a02
   * @param priority 优先级，1-5，数字越高优先级越高
   * @return 服务器应答结果：{"code":"返回码","message":"返回消息","reqcode":"请求编号,"data":"自定义返回"}
   * @throws Exception 如果连接失败抛出
   */
  public static String request(String url, String taskCode, String taskTyp, String start, String end, String priority) throws Exception{
    if (url == null || start == null ||end ==null || priority == null){
      throw new NullPointerException("参数不能为null");
    }
    List<String> userCallCodePath = new ArrayList<>();
    userCallCodePath.add(start);
    userCallCodePath.add(end);
    SchedulingTaskPackage task = new SchedulingTaskPackage(taskCode, taskTyp,null,userCallCodePath, priority);
    return HttpPost.send(url, JSONObject.toJSONString(task));
  }

}