package com.honda.intertest.pojo;

import org.springframework.stereotype.Service;

@Service
public class ErrorDetailPojo {
	private String errorCode;
	private String errorDesc;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
