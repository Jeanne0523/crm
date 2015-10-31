package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.dao.web.Servlets;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerService;
import com.atguigu.crm.service.DictService;
import com.atuigu.crm.entity.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerHandler extends BaseHandler<Customer>{

	@Autowired
	private CustomerService customerService;
	@Autowired
	private DictService dictService;
	
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false,defaultValue="1") String pageNoStr,
			HttpServletRequest request){
		super.list(pageNoStr, request);
		request.setAttribute("regions", dictService.getValues("地区"));
		request.setAttribute("levels", dictService.getValues("客户等级"));
		
		//5. 返回结果.
		return "customer/list";
	}
	
}
