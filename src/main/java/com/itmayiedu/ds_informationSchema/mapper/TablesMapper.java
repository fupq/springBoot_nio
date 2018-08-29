package com.itmayiedu.ds_informationSchema.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.itmayiedu.ds_informationSchema.entity.Tables;

public interface TablesMapper {

	@Select("SELECT * FROM information_schema.TABLES t WHERE t.table_name = #{tableName}")
	public Tables findTablesByName(@Param("tableName")String tableName);
	
	@Select("SELECT COUNT(1) FROM information_schema.TABLES")
	public int findTableCount();
}
