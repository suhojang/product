package com.kakaopay.product.error;

public class AlreadyRegisterException extends Exception{
	public AlreadyRegisterException() {
	}

	public AlreadyRegisterException(String message) {
		super(message);
	}
}
