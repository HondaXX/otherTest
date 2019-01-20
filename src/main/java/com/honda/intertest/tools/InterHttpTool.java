package com.honda.intertest.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Component;

import com.honda.intertest.dao.ErrorDetail;
import com.honda.intertest.dao.GetParamValue;
import com.honda.intertest.dao.InterInfo;
import com.honda.intertest.pojo.ErrorDetailPojo;
import com.honda.intertest.pojo.InterPojo;
import com.honda.intertest.pojo.ResponsePojo;
import com.honda.intertest.pojo.SqlVo;
import com.honda.intertest.services.ResponseDeal;

@Component
public class InterHttpTool {
	//init errordesc
	static Map<String, ErrorDetailPojo> errorMap = new HashMap<String, ErrorDetailPojo>();
	public static Map<String, ErrorDetailPojo> getAllErrorDetail(ErrorDetail ed) {
		List<ErrorDetailPojo> errorList = ed.getAllError();
		for(ErrorDetailPojo errorPojo : errorList) {
			errorMap.put(errorPojo.getErrorCode(), errorPojo);
		}
		return errorMap;
	}
	
	
	public static Map<String, ResponsePojo> interHttpReq(InterInfo ii, GetParamValue gpv, SqlVo sqlVo) {
		Map<String, ResponsePojo> resMap = new HashMap<String, ResponsePojo>();
		
		StringEntity se = null;
		ResponseDeal rd;
		ResponsePojo rp = new ResponsePojo();
		
		List<InterPojo> interList = ii.getAllInterInfo();
		if (interList.size() == 0) {
			rd = ResponseDeal.DB_INTER_INFO_NULL;  //("DB", "02", "W")
			String resCode = rp.getResCode(rd);
			rp.buildRetrunError(resCode, rp.getErrorDesc(resCode, errorMap));
			resMap.put("All", rp);
			return resMap;
		}else {
			for(InterPojo ip : interList) {
				String caseId  = ip.getCaseId().toString();
				System.out.println("====>start case: " + caseId);
				if (ip.getNeedDesignReq().equals("1")) {
					//change reqstr to map
					Map<String, Object> reqMap = StrDealTool.changeStrsToMap(ip.getRequestJson(), caseId);
					//change sql and user to map
					Map<String, Object> reqParamSqlMap = StrDealTool.changeStrsToMap(ip.getReqParam(), caseId);
					if (null == reqMap || null == reqParamSqlMap) {
						rd = ResponseDeal.INTER_INFO_LESS;
						String resCode = rp.getResCode(rd);
						rp.buildRetrunError(resCode, rp.getErrorDesc(resCode, errorMap));
						resMap.put(caseId, rp);
						continue;
					}
					//paramAndValueMap
					Map<String, Object> paramAndValueMap = BindParamTool.getValueForParam(reqParamSqlMap, gpv, sqlVo);
					if (null == paramAndValueMap) {
						rd = ResponseDeal.DB_PARAM_VALUE_NULL;
						String resCode = rp.getResCode(rd);
						rp.buildRetrunError(resCode, rp.getErrorDesc(resCode, errorMap));
						resMap.put(caseId, rp);
						continue;
					}
					//here get all request param value if there is a "#", and replace it's value
					Map<String, Object> trueReqMap = BindParamTool.replaceSettingParam(paramAndValueMap, reqMap);
					if (null == trueReqMap) {
						System.out.println("parameterization error: " + caseId);
						rd = ResponseDeal.INTER_PARAM_ZIP_ERROR;
						String resCode = rp.getResCode(rd);
						rp.buildRetrunError(resCode, rp.getErrorDesc(resCode, errorMap));
						resMap.put(caseId, rp);
						continue;
					}
					//after param zip start to request
					String trueReqStr = trueReqMap.toString();
					se = RequestTool.setEntity(trueReqStr, caseId);
				}else {
					se = RequestTool.setEntity(ip.getRequestJson(), caseId);
				}
				if (null == se) {
					rd = ResponseDeal.SYS_NET_SETENT_ERROR;
					String resCode = rp.getResCode(rd);
					rp.buildRetrunError(resCode, rp.getErrorDesc(resCode, errorMap));
					resMap.put(caseId, rp);
					continue;
				}
				
				//update sql if need be init, if error then exit this case
				if (ip.getNeedInit().equals("1")) {
					if(!(JdbcTool.operateDB(ip.getInitCode(), caseId, sqlVo, gpv))) {
						rd = ResponseDeal.DB_OPERATE_BEF_ERROR;
						String resCode = rp.getResCode(rd);
						rp.buildRetrunError(resCode, rp.getErrorDesc(resCode, errorMap));
						resMap.put(caseId, rp);
						continue;
					}
				}
				
				//get the request address
				String requestUrl = ip.getDNS() + ip.getInterUrl();
				
				//request 
				String resStr = RequestTool.requestServer(ip.getRequestMethod(), requestUrl, caseId, se);
				
				if (!StringUtils.isBlank(resStr)) {
					//the return str of request to map 
					Map<String, Object> resTrueMap = StrDealTool.changeStrsToMap(resStr, caseId);
					if (null == resTrueMap) {
						rd = ResponseDeal.SYS_NET_GETRES_ERROR;
						String resCode = rp.getResCode(rd);
						rp.buildRetrunError(resCode, rp.getErrorDesc(resCode, errorMap));
						resMap.put(caseId, rp);
						continue;
					}
					//if don't need to change param, used this to compare the result
					Map<String, Object> expectMap = StrDealTool.changeStrsToMap(ip.getExpectRes(), caseId);
					if (ip.getNeedDesignRes().equals("1")) {
						//change the expectRes without unknow param
						Map<String, Object> resParamSqlMap = StrDealTool.changeStrsToMap(ip.getResParam(), caseId);
						Map<String, Object> paramAndValueMap = BindParamTool.getValueForParam(resParamSqlMap, gpv, sqlVo);
						Map<String, Object> expectTrueMap = BindParamTool.replaceSettingParam(paramAndValueMap, expectMap);
						Boolean res = ResCompareTool.compareRes(resTrueMap, expectTrueMap, caseId);
						if (res == true) {
							rd = ResponseDeal.SYS_RESULT_SUCC;
							ResponsePojo rp2 = new ResponsePojo();
							String resCode = rp2.getResCode(rd);
							rp2.buildRetrunError(resCode, rp2.getErrorDesc(resCode, errorMap));
							resMap.put(caseId, rp2);
							continue;
						}else {
							rd = ResponseDeal.INTER_RES_NO_SAME;
							String resCode = rp.getResCode(rd);
							rp.buildRetrunError(resCode, rp.getErrorDesc(resCode, errorMap));
							resMap.put(caseId, rp);
							continue;
						}
					}
					else {
						Boolean res = ResCompareTool.compareRes(resTrueMap, expectMap, caseId);
						if (res == true) {
							rd = ResponseDeal.SYS_RESULT_SUCC;
							String resCode = rp.getResCode(rd);
							rp.buildRetrunError(resCode, rp.getErrorDesc(resCode, errorMap));
							resMap.put(caseId, rp);
							continue;
						}else {
							rd = ResponseDeal.INTER_RES_NO_SAME;
							ResponsePojo rp1 = new ResponsePojo();
							String resCode = rp1.getResCode(rd);
							rp1.buildRetrunError(resCode, rp1.getErrorDesc(resCode, errorMap));
							resMap.put(caseId, rp1);
							continue;
						}
					}
				}else {
					rd = ResponseDeal.SYS_NET_UNKNOW_ERROR;
					String resCode = rp.getResCode(rd);
					rp.buildRetrunError(resCode, rp.getErrorDesc(resCode, errorMap));
					resMap.put(caseId, rp);
					continue;
				}
			}
		}
		return resMap;
	}
}
