package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.dao.web.Servlets;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.BaseService;

public class BaseHandler<T> {

	@Autowired
	protected BaseService<T> service;
	
	public String list(String pageNoStr,
			HttpServletRequest request){
		int pageNo = 1;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		//1. 获取以 search_ 开头的请求参数名的请求参数的 Map
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		
		//2. 调用 Service 方法得到 Page 对象
		Page<T> page = new Page<>(pageNo);
		page = service.getPage(page, params);
		
		//3. 把 Page 对象放入到 Map 中
		request.setAttribute("page", page);
		
		//4. 把请求参数在编码成一个查询的字符串, 并放入到 request 中传回页面
		String queryString = Servlets.encodeParameterStringWithPrefix(params, "search_");
		request.setAttribute("queryString", queryString);
		
		//5. 返回结果.
		return null;
	}
	
}
