package com.atguigu.crm.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.atuigu.crm.entity.Customer;

@Service
public class CustomerService extends BaseService<Customer>{

	@Override
	protected void changeParams(Map<String, Object> params) {
		params.put("managerName", params.get("manager.name"));
		params.remove("manager.name");
	}
	
	/*
	@Transactional
	public Page<Customer> getPage(Page<Customer> page,
			Map<String, Object> params) {
		// 1. 把 params 转为 PropertyFilter 的集合
		List<PropertyFilter> filters = PropertyFilter
				.parseParamsToFilters(params);

		// 2. 把 PropertyFilter 的集合转为查询条件对应的 Map 对象.
		Map<String, Object> params2 = Servlets
				.parseFiltersToMyBatisParams(filters);
		
		params2.put("managerName", params2.get("manager.name"));
		params2.remove("manager.name");
		
		// 3. 调用 Mapper 方法得到分页相关的参数
		long totalElements = customerMapper.getTotalElements(params2);
		page.setTotal(totalElements);

		int fromIndex = (page.getNumber() - 1) * page.getSize() + 1;
		int endIndex = fromIndex + page.getSize();
		params2.put("fromIndex", fromIndex);
		params2.put("endIndex", endIndex);
		List<Customer> content = customerMapper.getContent(params2);
		page.setContent(content);

		// 4. 返回 Page
		return page;
	}
	*/
}
