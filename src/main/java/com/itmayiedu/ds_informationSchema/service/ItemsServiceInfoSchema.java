package com.itmayiedu.ds_informationSchema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itmayiedu.ds_fpq.service.ItemsServicefpq;
import com.itmayiedu.ds_informationSchema.mapper.ItemsMapperInfor;

@Service
public class ItemsServiceInfoSchema {

	@Autowired
	private ItemsMapperInfor itemsMapperInfor;
	
	@Autowired
	private ItemsServicefpq itemsServicefpq;
	
//	@Transactional
	public int addItem(String name,String detail){
		
		int count = itemsMapperInfor.addItems(name, detail);
//		float fl = 12/0;
		return count;
	}
	
	public int infoAddFpqService(String name,String detail){
		System.out.println("在infoScheme数据源中开始调用itemsServicefpq的服务添加items表中的数据--开始，name=" + name);
		int count = itemsServicefpq.addItem(name,detail);
		System.out.println("在infoScheme数据源中开始调用itemsServicefpq的服务添加items表中的数据--完成，name=" + name);
		return count;
	}
	
	public int findItemsCount(){
		System.out.println("在infoScheme数据源中开始调用items的mapper查询items表商品的数据量");
		int count = itemsMapperInfor.findItemCount();
		return count;
	}
}
