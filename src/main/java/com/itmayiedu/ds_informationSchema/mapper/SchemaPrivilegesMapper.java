package com.itmayiedu.ds_informationSchema.mapper;

import org.apache.ibatis.annotations.Select;

import com.itmayiedu.ds_informationSchema.entity.SchemaPrivileges;

public interface SchemaPrivilegesMapper {

	@Select("SELECT * FROM information_schema.`SCHEMA_PRIVILEGES` t WHERE t.`TABLE_SCHEMA` = 'mysql' AND t.`PRIVILEGE_TYPE` = 'SELECT'")
	public SchemaPrivileges findSchemaPrivileges();
	
}
