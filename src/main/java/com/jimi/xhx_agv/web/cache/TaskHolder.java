package com.jimi.xhx_agv.web.cache;

import java.util.HashMap;
import java.util.Map;

public class TaskHolder {

	private static Map<String, String> map = new HashMap<>();
	
	
	public static void add(String id, String startAndEndPositionId) {
		map.put(id, startAndEndPositionId);
	}
	
	
	public static void remove(String id) {
		map.remove(id);
	}
	
	
	public static String get(String id) {
		return map.get(id);
	}
}
