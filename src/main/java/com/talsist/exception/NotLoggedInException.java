package com.talsist.exception;

public class NotLoggedInException extends RuntimeException {
	
	@Override
	public String getMessage() {
		return "로그인 하세요";
	}
	
}
