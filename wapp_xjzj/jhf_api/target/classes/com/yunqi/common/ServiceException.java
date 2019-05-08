package com.yunqi.common;

public class ServiceException extends Exception {
	private static final long serialVersionUID = 8670135969660230761L;

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String message) {
		super(message);
	}
}