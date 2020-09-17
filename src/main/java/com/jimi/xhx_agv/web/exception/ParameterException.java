package com.jimi.xhx_agv.web.exception;

/**
 * 请求的参数异常
 * <br>
 * <b>2018年6月2日</b>
 * @author 沫熊工作室 <a href="http://www.darhao.cc">www.darhao.cc</a>
 */
public class ParameterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParameterException(String message) {
		super(message);
	}
	
}
