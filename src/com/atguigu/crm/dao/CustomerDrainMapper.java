package com.atguigu.crm.dao;

import org.apache.ibatis.annotations.Update;

public interface CustomerDrainMapper {

	@Update("{call drain_procedure2}")
	public void callDrainProcedure();
	
}
