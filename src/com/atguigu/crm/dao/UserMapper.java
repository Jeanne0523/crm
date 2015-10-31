package com.atguigu.crm.dao;

import org.apache.ibatis.annotations.Param;

import com.atuigu.crm.entity.User;

public interface UserMapper {
	
	User getByName(@Param("name") String name);
	
}