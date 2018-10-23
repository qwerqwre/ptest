package com.yyh.pf.exception;

//Exception을 상속받아서 예외 클래스가 됨
/**
* 비밀 번호가 안 맞을 때
* @author user
*
*/
public class PasswordMissMatchException extends Exception{
	private String message;
	
	// 기본 생성자
	// 기본 message
	public PasswordMissMatchException() { 
		this.message = "비밀번호가 달라요";
	}

	// 생성자는 반환이 없다
	// 생성자는 메서드이다
	// 원하는 message 넣기
	public PasswordMissMatchException(String msg) {
		this.message = msg;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
