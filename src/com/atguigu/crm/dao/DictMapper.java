package com.atguigu.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface DictMapper {

	@Select("SELECT item FROM dicts WHERE type = #{type}")
	List<String> getValues(String type);

}
