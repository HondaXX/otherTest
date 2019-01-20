package com.honda.intertest.services;

public class ResponseBase {
	private String origin;
	private String code;
	private String errorLevel;
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getErrorLevel() {
		return errorLevel;
	}
	public void setErrorLevel(String errorLevel) {
		this.errorLevel = errorLevel;
	}
	
	public ResponseBase(String origin, String code, String errorLevel) {
		super();
		this.origin = origin;
		this.code = code;
		this.errorLevel = errorLevel;
	}

}
