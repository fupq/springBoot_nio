package com.itmayiedu.ds_informationSchema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itmayiedu.ds_informationSchema.entity.SchemaPrivileges;
import com.itmayiedu.ds_informationSchema.mapper.SchemaPrivilegesMapper;

@Service
public class SchemaPrivilegesService {

	@Autowired
	SchemaPrivilegesMapper schemaPrivilegesMapper;
	
	public SchemaPrivileges findSchemaPrivileges(){
		SchemaPrivileges  schemaPrivileges  = schemaPrivilegesMapper.findSchemaPrivileges();
		System.out.println("查询到的schema权限信息是：" + schemaPrivileges.printInfo());
		return schemaPrivileges;
	}
	
	
}
