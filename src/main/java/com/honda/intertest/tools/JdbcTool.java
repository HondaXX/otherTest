package com.honda.intertest.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.honda.intertest.dao.GetParamValue;
import com.honda.intertest.pojo.SqlVo;

public class JdbcTool {
	public static SqlSession initDataXml() throws IOException{
		SqlSessionFactory ssf;
		String resource = "spring-mybatis.xml";
		InputStream is = Resources.getResourceAsStream(resource);
		ssf = new SqlSessionFactoryBuilder().build(is);
		SqlSession sqls = ssf.openSession();
		return sqls;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean operateDB(String dbCode, String caseId, SqlVo sqlVo, GetParamValue gpv) {
		Map<String, Object> dbCodeMap = StrDealTool.changeStrsToMap(dbCode, caseId);
		if (null == dbCodeMap) {
			System.out.println("====>change db strCode to map error: "+ caseId);
			return false;
		}else {
			for(String dbUser : dbCodeMap.keySet()) {
				List<String> sqlList = (List<String>) dbCodeMap.get(dbUser);
				for(String sqlStr : sqlList) {
					sqlVo.setSqlStr(sqlStr);
					try {
						gpv.updateSql(sqlVo);
					} catch (Exception e) {
						System.out.println("====>update sql error which caseId: " + caseId + "\nsql: "+ sqlStr);
						return false;
					}
				}
			}
		}
		return true;
	}
}
