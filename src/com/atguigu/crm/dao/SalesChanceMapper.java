package com.atguigu.crm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.atuigu.crm.entity.SalesChance;

public interface SalesChanceMapper extends BaseMapper<SalesChance>{

	SalesChance getById(@Param("id") Integer id);
	
	long getTotalElements(Map<String, Object> params2);
	
	List<SalesChance> getContent(Map<String, Object> params2);

	@Delete("DELETE FROM sales_chances WHERE id = #{id}")
	void delete(@Param("id") Integer id);

	void update(SalesChance chance);

	SalesChance get(@Param("id") Integer id);

	void save(SalesChance chance);

	
	//编号  客户名称  概要  联系人  联系人电话  创建时间  
	//List<SalesChance> getContent(Map<String, Object> params);
	
	@Select("SELECT count(id) FROM sales_chances")
	long getTotalElements();

}
