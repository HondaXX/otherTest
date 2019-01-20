package com.honda.intertest.pojo;

import java.util.HashMap;
import java.util.Map;

import com.honda.intertest.services.ResponseDeal;

public class ResponsePojo extends HashMap<String, Object>{
	private static final long serialVersionUID = 870714850748751859L;
	
	public String getResCode(ResponseDeal rd) {
		return rd.getOrigin() + "0" + rd.getCode();
	}
	
	public void buildRetrunError(String resCode, String errorDesc) {
		setResultCode(resCode);
		setErrorDesc(errorDesc);
	}
	
	//set resultCode
	public void setResultCode(String resCode) {
        this.put("resCode", resCode);
    }
	public String getResultCode(String resCode) {
        return resCode;
    }
	
	//if success, set resultdata
	public void setResultData(String key, Object data) {
		this.put(key, data);
	}
	public Object getResultData(String key) {
		Object value = getData(key);
        return value;
	}
	public Object getData(String key) {
        Object value = get(key);
        return value;
    }
	
	//fail, set errordesc
	public void setErrorDesc(String	errorDesc) {
		this.put("errDesc", errorDesc);
	}
	public String getErrorDesc(String resCode, Map<String, ErrorDetailPojo> errorMap) {
//		System.out.println(errorMap.get(resCode).getErrorDesc());
		return errorMap.get(resCode).getErrorDesc();
	}
}
