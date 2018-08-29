//package com.itmayiedu.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.itmayiedu.entity.Items;
//import com.itmayiedu.mapper.ItemsMapper;
//
//@Service
//public class ItemsService {
//
//	@Autowired
//	private ItemsMapper itemsMapper;
//	
//	/**
//	 * addItem:添加items的服务<br/> 
//	 * TODO(在方法上添加@Transactional的作用是方法作为一个事物来处理，具有原子性，若方法中任何一个地方报错则方法全部回滚).<br/> 
//	 * 
//	 * @author xn042142 付品欣
//	 * @param name item的名称
//	 * @param detail itme的详细信息
//	 * @return 
//	 * @since JDK 1.8
//	 * 2017年11月15日 下午1:56:58
//	 */
//	@Transactional
//	public int addItem(String name,String detail){
//		
//		int count = itemsMapper.addItems(name, detail);
////		float fl = 12/0;
//		return count;
//	}
//	
//	public void makeError(){
//		int a = 12/0;
//	}
//	
//	/**
//	 * findItemsCount:查询表items中总条数<br/> 
//	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
//	 * 
//	 * @author xn042142 付品欣
//	 * @return 
//	 * @since JDK 1.8
//	 * 2017年11月15日 下午3:59:05
//	 */
//	public int findItemsCount(){
//		return itemsMapper.findItemCount();
//	}
//	
//	/**
//	 * findItems:根据名称查询产品<br/> 
//	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
//	 * 
//	 * @author xn042142 付品欣
//	 * @param name
//	 * @return 
//	 * @since JDK 1.8
//	 * 2017年11月15日 下午5:04:03
//	 */
//	public Items findItems(String name){
//		Items item= itemsMapper.findItem(name);
//		System.out.println(item.toString());
//		return item;
//	}
//}
