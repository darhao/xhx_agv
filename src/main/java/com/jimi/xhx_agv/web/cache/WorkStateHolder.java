package com.jimi.xhx_agv.web.cache;

public class WorkStateHolder {

	private static boolean isLoadAreaWork = false;
	
	private static boolean isUnloadAreaWork = false;
	
	
	public static boolean isLoadAreaWork() {
		return isLoadAreaWork;	
	}
	
	
	public static void setLoadAreaWork(boolean state) {
		isLoadAreaWork = state;
	}

	
	public static boolean isUnloadAreaWork() {
		return isUnloadAreaWork;	
	}
	
	
	public static void setUnloadAreaWork(boolean state) {
		isUnloadAreaWork = state;
	}
}
