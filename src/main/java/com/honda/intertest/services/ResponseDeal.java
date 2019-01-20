package com.honda.intertest.services;


public class ResponseDeal extends ResponseBase{
	
	public ResponseDeal(String origin, String code, String errorLevel) {
		super(origin, code, errorLevel);
	}
	
	public static final ResponseDeal SYS_RESULT_SUCC = new ResponseDeal("SYS", "00", "I");
	public static final ResponseDeal SYS_UNEXPECTED_EXCEPTION = new ResponseDeal("SYS", "01", "E");
	public static final ResponseDeal SYS_NET_SETENT_ERROR = new ResponseDeal("SYS", "02", "E");
	public static final ResponseDeal SYS_NET_GETRES_ERROR = new ResponseDeal("SYS", "03", "E");
	public static final ResponseDeal SYS_NET_UNKNOW_ERROR = new ResponseDeal("SYS", "04", "E");
	
	public static final ResponseDeal DB_CONNECT_ERROR = new ResponseDeal("DB", "01", "E");
	public static final ResponseDeal DB_INTER_INFO_NULL = new ResponseDeal("DB", "02", "W");
	public static final ResponseDeal DB_PARAM_VALUE_NULL = new ResponseDeal("DB", "03", "W");
	public static final ResponseDeal DB_OPERATE_BEF_ERROR = new ResponseDeal("DB", "04", "E");
	
	public static final ResponseDeal INTER_INFO_LESS = new ResponseDeal("INT", "01", "E");
	public static final ResponseDeal INTER_PARAM_ZIP_ERROR = new ResponseDeal("INT", "02", "E");
	public static final ResponseDeal INTER_RES_NO_SAME = new ResponseDeal("INT", "03", "E");
}
