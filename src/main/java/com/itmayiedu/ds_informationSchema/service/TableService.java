package com.itmayiedu.ds_informationSchema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itmayiedu.ds_informationSchema.entity.Tables;
import com.itmayiedu.ds_informationSchema.mapper.TablesMapper;

@Service
public class TableService {

	@Autowired
	public TablesMapper tablesMapper;
	
	/**
	 * 根据表名称查询表信息
	 * @param tableName
	 * @return
	 */
	public Tables findTableByName(String tableName){
		System.out.println("您查询的表的名称是：" + tableName);
		Tables table = tablesMapper.findTablesByName(tableName);
		System.out.println("您查询的表：" + tableName + "的信息是：" + table.printInfo());
		return table;
	}
	
	/**
	 * 查询mysql表中表的数量
	 * @return
	 */
	public int findTableCount(){
		int count = tablesMapper.findTableCount();
		System.out.println("mysql数据库中有：" + count + "张表!");
		return count;
	}
}
