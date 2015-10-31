package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.dao.BaseMapper;
import com.atguigu.crm.dao.web.Servlets;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;

public abstract class BaseService<T> {
	
	@Autowired
	protected BaseMapper<T> mapper;
	
	protected void changeParams(Map<String, Object> params){}
	
	@Transactional(readOnly=true)
	public Page<T> getPage(Page<T> page,
			Map<String, Object> params) {
		//1. 把 params 转为 PropertyFilter 的集合
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(params);
		
		//2. 把 PropertyFilter 的集合转为查询条件对应的 Map 对象.
		Map<String, Object> params2 = Servlets.parseFiltersToMyBatisParams(filters);
		changeParams(params2);
		
		//3. 调用 Mapper 方法得到分页相关的参数
		long totalElements = mapper.getTotalElements(params2);
		page.setTotal(totalElements);
		
		int fromIndex = (page.getNumber() - 1) * page.getSize() + 1;
		int endIndex = fromIndex + page.getSize();
		params2.put("fromIndex", fromIndex);
		params2.put("endIndex", endIndex);
		List<T> content = mapper.getContent(params2);
		page.setContent(content);
		
		//4. 返回 Page
		return page;
	}
	
}
