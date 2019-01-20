package com.honda.intertest.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.honda.intertest.pojo.InterPojo;

@MapperScan
public interface InterInfo {
	public List<InterPojo> getAllInterInfo();
	public InterPojo getCaseById(Map<String, Object> dbMap);
}
