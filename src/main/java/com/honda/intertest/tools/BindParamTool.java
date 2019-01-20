package com.honda.intertest.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.honda.intertest.dao.GetParamValue;
import com.honda.intertest.pojo.SqlVo;

public class BindParamTool {
	
	//this method get all db and param for this db--(but no use this method)
	@SuppressWarnings("unchecked")
	public static Map<String, List<String>> getReqParamSet(Map<String, Object> reqParamMap){
		Map<String, List<String>> pfuMap = new HashMap<String, List<String>>();
		for (String dbUser : reqParamMap.keySet()) {
			List<String> paramList = new ArrayList<String>();
			Map<String, String> sqlMap = (Map<String, String>) reqParamMap.get(dbUser);
			for(String param : sqlMap.keySet()) {
				paramList.add(param);
			}
			pfuMap.put(dbUser, paramList);
		}
		return pfuMap;
	}
	
	//this method get param and get value from db
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getValueForParam(Map<String, Object> reqParamMap, GetParamValue gpv, SqlVo sqlVo) {
		Map<String, Object> vfpMap = new HashMap<String, Object>();
//		Set<String> dbUserSet = reqParamMap.keySet();
		for (String dbUser : reqParamMap.keySet()) {
			System.out.println("====>here must be choose user for db!");
			Map<String, String> sqlMap = (Map<String, String>) reqParamMap.get(dbUser);
			for(String param : sqlMap.keySet()) {
				String sql = sqlMap.get(param);
				sqlVo.setSqlStr(sql);
				Object value = gpv.getParamValue(sqlVo);
				if (null == value) {
					System.out.println("====>get value from db error which param:" + param + "\nsql: " + sql);
					return null;
				}
				vfpMap.put(param, value);
			}
		}
		return vfpMap;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> replaceSettingParam(Map<String, Object> paramAndValueMap, Map<String, Object> reqMap) {
		Set<String> paramSet = paramAndValueMap.keySet();
		Set<String> keySetFirst = reqMap.keySet();
		
		List<String> mapParamList = new ArrayList<String>();
		for(String paramReq : reqMap.keySet()) {
			if (reqMap.get(paramReq) instanceof Map) {
				mapParamList.add(paramReq);
			}
		}
	
		for(String param : paramSet) {
			if (keySetFirst.contains(param) && reqMap.get(param).equals("#")) {
					reqMap.put(param, paramAndValueMap.get(param));
					return reqMap;
			}else {
				for(String mapParam : mapParamList) {
					Map<String, Object> secondMap = (Map<String, Object>) reqMap.get(mapParam);
					Set<String> keySetSecond = secondMap.keySet();
					if (keySetSecond.contains(param) && secondMap.get(param).equals("#")) {
						secondMap.put(param, paramAndValueMap.get(param));
						reqMap.put(mapParam, secondMap);
						return reqMap;
					}else {
						System.out.println("====>can't find param: " + param + ", check the requestJson!");
						return null;
					}
				}
				
			}
		}
		return null;
	}
}
