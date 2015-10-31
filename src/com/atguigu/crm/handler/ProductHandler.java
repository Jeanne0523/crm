package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.dao.web.Servlets;
import com.atguigu.crm.service.jpa.ProductService;

@RequestMapping("/product")
@Controller
public class ProductHandler {

	@Autowired
	private ProductService productService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request, 
			@RequestParam(value="page", required=false, defaultValue="1") String pageNoStr){
		int pageNo = 1;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		//1. 获取以 search_ 开头的请求参数名的请求参数的 Map
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		
		//2. 调用 Service 方法得到 Page 对象
		Page page = productService.getPage(pageNo, 5, params);
		
		//3. 把 Page 对象放入到 Map 中
		request.setAttribute("page", page);
		
		//4. 把请求参数在编码成一个查询的字符串, 并放入到 request 中传回页面
		String queryString = Servlets.encodeParameterStringWithPrefix(params, "search_");
		request.setAttribute("queryString", queryString);
		
		return "product/list";
	}
}
