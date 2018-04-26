package com.prestamos.api;

public class PrestamosException extends RuntimeException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8093634171846946687L;

	public PrestamosException() {
		super();
	}

	public PrestamosException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PrestamosException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrestamosException(String message) {
		super(message);
	}

	public PrestamosException(Throwable cause) {
		super(cause);
	}

	
	
}
