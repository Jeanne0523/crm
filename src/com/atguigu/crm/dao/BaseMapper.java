package com.atguigu.crm.dao;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

	long getTotalElements(Map<String, Object> params);

	List<T> getContent(Map<String, Object> params);
	
}