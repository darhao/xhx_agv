package com.jimi.xhx_agv.web.exception;

/**
 * 权限不足异常
 * <br>
 * <b>2018年6月2日</b>
 * @author 沫熊工作室 <a href="http://www.darhao.cc">www.darhao.cc</a>
 */
public class AccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccessException(String message) {
		super(message);
	}
	
}
