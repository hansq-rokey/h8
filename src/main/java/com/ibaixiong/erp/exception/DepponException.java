package com.ibaixiong.erp.exception;

/**
 * 德邦接口不通问题
 * 
 * @author zhaolei
 * @Description TODO
 * @date 2015年11月5日
 *
 */
public class DepponException extends RuntimeException {



	/**
	 * 
	 */
	private static final long serialVersionUID = -7960455949870439134L;

	public DepponException() {
		super();
	}

	public DepponException(String message) {
		super(message);
	}

	public DepponException(Throwable cause) {
		super(cause);
	}

	public DepponException(String message, Throwable cause) {
		super(message, cause);
	}

}
