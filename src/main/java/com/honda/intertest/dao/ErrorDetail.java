package com.honda.intertest.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import com.honda.intertest.pojo.ErrorDetailPojo;

@Service
@MapperScan
public interface ErrorDetail {
	public List<ErrorDetailPojo> getAllError();
}
