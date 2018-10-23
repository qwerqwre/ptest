package com.yyh.pf.exception;

/**
 * 아이디가 존재하지 않을 때
 * @author user
 *
 */
public class MemberNotFoundException extends Exception{
	private String message;
	
	public MemberNotFoundException() {
		this.message = "아이디가 존재하지않습니다";
	}
	
	public MemberNotFoundException(String msg) {
		this.message = msg;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
