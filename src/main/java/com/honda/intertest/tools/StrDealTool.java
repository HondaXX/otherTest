package com.honda.intertest.tools;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class StrDealTool {
	//change string to map 
	public static Map<String, Object> changeStrsToMap(String str, String caseId) {
		if (StringUtils.isBlank(str)) {
			System.out.println("====>the string want to change to map is null, caseId: " + caseId);
			return null;
		}else {
			Gson gs = new Gson();
			Map<String, Object> resultMap = gs.fromJson(str, new TypeToken<Map<String, Object>>(){}.getType());
			return resultMap;
		}
	}
	
}
