package com.honda.intertest.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.honda.intertest.pojo.SqlVo;

@MapperScan
public interface GetParamValue {
	public String getParamValue(SqlVo sqlVo);
	public Boolean updateSql(SqlVo sqlVo);
}
