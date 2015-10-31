package com.atguigu.crm.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.crm.dao.SalesChanceMapper;

public class SalesChanceMapperTest {
	
	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		SalesChanceMapper salesChanceMapper = ctx.getBean(SalesChanceMapper.class);
		
		Object result = salesChanceMapper.getById(7040);
		
		System.out.println(result);
	}
	
}
