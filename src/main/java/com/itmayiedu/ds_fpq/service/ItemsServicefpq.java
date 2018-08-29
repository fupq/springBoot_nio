package com.itmayiedu.ds_fpq.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itmayiedu.ds_fpq.entity.Items;
import com.itmayiedu.ds_fpq.mapper.ItemsMapperfpq;
import com.itmayiedu.ds_informationSchema.mapper.ItemsMapperInfor;
import com.itmayiedu.ds_informationSchema.service.ItemsServiceInfoSchema;

@Service
public class ItemsServicefpq{

	private static Logger logger = Logger.getLogger(ItemsServicefpq.class);
	
	@Autowired
	private ItemsMapperfpq itemsMapperfpq;
	
	@Autowired
	private ItemsMapperInfor itemsMapperInfor;
	
	@Autowired
	private ItemsServiceInfoSchema itemsService;
	
	@Autowired
	private CacheManager cacheManager;
	
	/**
	 * 读取配置文件application.properties中自定义参数mysql.datasource.informationSchema.url的值
	 */
	@Value("${mysql.datasource.informationSchema.url}")
	private String db_informationSchema;
	
	/**
	 * 读取配置文件application.properties中自定义参数mysql.datasource.fpq.url的值
	 */
	@Value("${mysql.datasource.fpq.url}")
	private String db_fpq;
	

	/**
	 * 读取配置文件log4j.properties中自定义参数log4j.appender.info.File的值
	 */
	@Value("${mysql.datasource.fpq.username}")
	private String springBootLog;
	/**
	 * FindInEHCatch:(使用ehcatch缓存数据测试). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param id
	 * @return 
	 * @since JDK 1.8
	 * 2017年12月8日 上午10:31:46
	 */
	public Items FindInEHCatch(Integer id){
		Items item = itemsMapperfpq.findInEhcatch(id);
		return item;
	}
	
	/**
	 * deleteFromEHCatch:(删除缓存). <br/> 
	 * TODO(需要定义：private CacheManager cacheManager;).<br/> 
	 * baseCache是在ehcache中定义的
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2017年12月8日 上午10:51:45
	 */
	public String deleteFromEHCatch(){
		cacheManager.getCache("baseCache").clear();
		return "success";
	}
	
	/**
	 * addItem:添加items的服务<br/> 
	 * TODO(在方法上添加@Transactional的作用是方法作为一个事物来处理，具有原子性，若方法中任何一个地方报错则方法全部回滚).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param name item的名称
	 * @param detail itme的详细信息
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月15日 下午1:56:58
	 */
	@Transactional
	public int addItem(String name,String detail){
		
		int count = itemsMapperfpq.addItems(name, detail);
//		float fl = 12/0;
		return count;
	}
	
	public void makeError(){
		int a = 12/0;
	}
	
	/**
	 * fpqAddInfo:(在fpq的数据源的server中调用info数据源的mapper). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param name
	 * @param detail
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 上午10:29:25
	 */
	public int fpqAddInfo(String name,String detail){		
		System.out.println("开始执行在fpq数据源服务中调用info数据源的mapper的事物操作");
		int count = itemsMapperInfor.addItems(name, detail);
		System.out.println("已经执行完毕在fpq数据源服务中调用info数据源的mapper的事物操作");
		return count;
	}
	
	/**
	 * fpqAddInfoService:(在fpq的数据源的server中调用info数据源的server添加itmes). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param name
	 * @param detail
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 上午11:53:22
	 */
	public int fpqAddInfoService(String name,String detail){		
		System.out.println("开始执行在fpq数据源服务中调用info数据源的mapper的事物操作");
		int count = itemsService.addItem(name, detail);
		System.out.println("已经执行完毕在fpq数据源服务中调用info数据源的mapper的事物操作");
		return count;
	}
	
	//在fpq的数据源范围内添加infoSchema的数据看是否能回滚
	public void fpqAddInfoSchema(String name,String detail){
//		System.out.println("开始执行完毕fpq数据源的事物操作");
//		itemsMapperfpq.addItems(name, detail);
//		System.out.println("已经执行完毕fpq数据源的事物操作");
		
		System.out.println("开始执行完毕infoSchema数据源的事物操作");
		itemsMapperInfor.addItems(name, detail);
		System.out.println("已经执行完毕infoSchema数据源的事物操作");
		
//		int a = 11/0;
//		System.out.println("开始执行完毕fpq数据源的事物操作");
//		itemsMapperfpq.addItems(name, detail);
//		System.out.println("已经执行完毕fpq数据源的事物操作");
	}
	
	/**
	 * findItemsCount:查询表items中总条数<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月15日 下午3:59:05
	 */
	public int findItemsCount(){
		return itemsMapperfpq.findItemCount();
	}
	
	/**
	 * findItems:根据名称查询产品<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param name
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月15日 下午5:04:03
	 */
	public Items findItems(String name){
		Items item= itemsMapperfpq.findItem();
		System.out.println("您查询的产品的名称是" + name + ",该产品的详细信息：" + item.printItemInfo());
		return item;
	}
	
	public Items findItemsByName(String name){
		Items item = itemsMapperfpq.findItemByName(name);
		System.out.println("您查询的产品的名称是" + name + ",该产品的详细信息：" + item.printItemInfo());
		return item;
	}
	
	/**
	 * sendSMS:(模式发送短信，使用多线程异步发送). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author fpq 付品欣
	 * @param count
	 * @return 
	 * @since JDK 1.8
	 * 2017年12月9日 上午12:04:17
	 */
	@Async
	public String sendSMS(int count){
		logger.info("发送短信执行开始... begin 2 ... ");
		int i=0;
		for(i=0;i<count;i++){
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error("休眠时报错：" + e.getMessage(),e);
			}
			logger.info("i=" + i);
		}
		logger.info("发送短信执行结束... end 3 ... ");
		return "共发送了" + i + "个短信！";
	}
	
	public String dateBaseInfo(){
		String dbInfo = "";
		logger.info("链接的第一个数据库是：" + this.db_fpq);
		logger.info("链接的第二个数据库是：" + this.db_informationSchema);
		logger.info("访问数据库的账号是：" + this.springBootLog);
		dbInfo = "目前链接了2个数据库，一个是:" + this.db_fpq + ",另一个是：" + this.db_informationSchema + "；访问数据库的账号是：" + this.springBootLog;
		return dbInfo;
	}
	
	
}
