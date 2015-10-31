package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.dao.SalesChanceMapper;
import com.atguigu.crm.orm.Page;
import com.atuigu.crm.entity.SalesChance;

@Service
public class SalesChanceService extends BaseService<SalesChance>{

	public SalesChanceMapper getSalesChanceMapper() {
		return (SalesChanceMapper) mapper;
	}
	
	/**
	 * 从页面上传入的参数:
	 * search_LIKES_custName=a&search_LIKES_title=b&search_LIKES_contact=c
	 * 
	 * 把其转为一个 Map:
	 * LIKES_custName:a,LIKES_title:b,LIKES_contact:c
	 * 
	 * 把其转为 PropertyFilter 的集合
	 * [custName,a,LIKE,S],[title,b,LIKE,S],[contact,c,LIKE,S]
	 * 
	 * 把其转为 mybatis 可用的 Map!
	 * 1. 把值转为目标类型后作为 Map 的 value, 根据比较的方式, 可以考虑把值进行适当的转化, 例如 LIKE 时添加 %%
	 * 2. 键: 即为 propertyName
	 */
	/*
	@Transactional(readOnly=true)
	public Page<SalesChance> getPage(Page<SalesChance> page,
			Map<String, Object> params) {
		//1. 把 params 转为 PropertyFilter 的集合
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(params);
		
		//2. 把 PropertyFilter 的集合转为查询条件对应的 Map 对象.
		Map<String, Object> params2 = Servlets.parseFiltersToMyBatisParams(filters);
		
		//3. 调用 Mapper 方法得到分页相关的参数
		long totalElements = getSalesChanceMapper().getTotalElements(params2);
		page.setTotal(totalElements);
		
		int fromIndex = (page.getNumber() - 1) * page.getSize() + 1;
		int endIndex = fromIndex + page.getSize();
		params2.put("fromIndex", fromIndex);
		params2.put("endIndex", endIndex);
		List<SalesChance> content = getSalesChanceMapper().getContent(params2);
		page.setContent(content);
		
		//4. 返回 Page
		return page;
	}
	*/
	
	@Transactional
	public void delete(Integer id) {
		getSalesChanceMapper().delete(id);
	}
	
	@Transactional
	public void update(SalesChance chance) {
		getSalesChanceMapper().update(chance);
	}
	
	@Transactional(readOnly=true)
	public SalesChance get(Integer id) {
		return getSalesChanceMapper().get(id);
	}
	
	@Transactional
	public void save(SalesChance chance) {
		getSalesChanceMapper().save(chance);
	}
	
	/**
	 * 若底层使用 myBatis, 则其分页的逻辑需要在 Service 的方法中完成. 
	 * 而底层若使用 hibernate 或 jpa, 则可以在 Dao 方法中完成分页逻辑. 
	 * 
	 */
	//不带查询条件的分页
	@Transactional(readOnly=true)
	public Page getPage(Page page){
		//1. 获取总的记录数
		long totalElements = getSalesChanceMapper().getTotalElements();
		page.setTotal(totalElements);
		
		//2. 获取当前页面的 content
		int fromIndex = (page.getNumber() - 1) * page.getSize() + 1;
		int endIndex = fromIndex + page.getSize();
		Map<String, Object> params = new HashMap<>();
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		List<SalesChance> content = getSalesChanceMapper().getContent(params);
		
		//3. 为 Page 的属性赋值
		page.setContent(content);
		
		//4. 返回 Page 对象
		return page;
	}

	
}
