package com.honda.intertest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.honda.intertest.dao.ErrorDetail;
import com.honda.intertest.dao.GetParamValue;
import com.honda.intertest.dao.InterInfo;
import com.honda.intertest.pojo.ResponsePojo;
import com.honda.intertest.pojo.SqlVo;
import com.honda.intertest.tools.InterHttpTool;

@Controller
@ResponseBody
public class InterController {
	@Autowired
	private SqlVo sqlVo;
	@Autowired
	private GetParamValue gpv;
	@Autowired
	private InterInfo ii;
	@Autowired
	private ErrorDetail ed;
	
	@RequestMapping(value = "/Inter/getInterInfo.json", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Map<String, ResponsePojo> getInterInfo(@RequestBody Map<String, Object> reqMap){
		InterHttpTool.getAllErrorDetail(ed);
		Map<String, ResponsePojo> resMap = InterHttpTool.interHttpReq(ii, gpv, sqlVo);
		return resMap;
	}
	
	@RequestMapping(value = "/Inter/test.json", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public void test(@RequestBody Map<String, Object> reqMap){
		
//		String sqlStr = "SELECT name FROM `tbl_user_base` where id = 2";
//		sqlVo.setSqlStr(sqlStr);
//		String back = gpv.getParamValue(sqlVo);
//		System.out.println(back);
//		
//		Map<String, Object> dbMap = new HashMap<String, Object>();
//		dbMap.put("caseId", 1);
//		InterPojo ip = ii.getCaseById(dbMap);
//		System.out.println(ip.toString());
	}
	
}