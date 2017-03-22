package com.ibaixiong.erp.exception;

/**
 * 库存不足异常类
 * 
 * @author yaoweiguo
 * @Email yaoweiguo@ibaixiong.com
 * @Description TODO
 * @date 2015年10月27日
 *
 */
public class LowStocksException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2303052077054185744L;

	public LowStocksException() {
		super();
	}

	public LowStocksException(String message) {
		super(message);
	}

	public LowStocksException(Throwable cause) {
		super(cause);
	}

	public LowStocksException(String message, Throwable cause) {
		super(message, cause);
	}

}
