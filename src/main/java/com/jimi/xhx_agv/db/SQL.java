package com.jimi.xhx_agv.db;

public class SQL {

	public static final String GET_USER_BY_NAME_AND_PASSWOR = "SELECT * FROM user WHERE user = ? AND password = ?";
	
	public static final String LIST_ALL_LOAD_POSITIONS = "SELECT * FROM position WHERE area = 0 AND type = 0";
	public static final String GET_A_EMPTY_SHELVES_STORE_POSITION_FROM_LOAD_AREA = "SELECT * FROM position WHERE area = 0 AND type = 1 AND goods_state = 1 AND is_lock = 0 LIMIT 1";
	public static final String GET_A_EMPTY_STORE_POSITION_FROM_LOAD_AREA = "SELECT * FROM position WHERE area = 0  AND type = 1 AND goods_state = 0 AND is_lock = 0 LIMIT 1";
	
	public static final String GET_A_EMPTY_STORE_POSITION_FROM_UNLOAD_AREA = "SELECT * FROM position WHERE area = 1  AND type = 1 AND goods_state = 0 AND is_lock = 0 LIMIT 1";
	public static final String LIST_ALL_UNLOAD_AREA_POSITIONS = "SELECT * FROM position WHERE area = 1";
	public static final String LIST_ALL_UNLOAD_AREA_UNLOAD_POSITIONS = "SELECT * FROM position WHERE area = 1 AND type =0";
	
	public static final String GET_ALL_HAVE_GOODS_STORE_POSITION_FROM_LOAD_AREA = "SELECT * FROM position WHERE area = 0 AND type = 1 AND goods_state = 2 AND is_lock = 0";
	public static final String GET_ALL_EMPTY_SHELVES_STORE_POSITION_FROM_UNLOAD_AREA = "SELECT * FROM position WHERE area = 1  AND type = 1 AND goods_state = 1 AND is_lock = 0";
	
}