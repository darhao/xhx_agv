package com.jimi.xhx_agv.web.exception;

/**
 * 发送短信失败异常
 */
public class ThirdPartException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public ThirdPartException(String message) {
            super(message);
        }

}
