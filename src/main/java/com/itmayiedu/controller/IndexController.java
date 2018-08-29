package com.itmayiedu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itmayiedu.ds_fpq.entity.Items;
import com.itmayiedu.ds_fpq.service.ItemsServicefpq;
import com.itmayiedu.ds_informationSchema.service.ItemsServiceInfoSchema;
import com.itmayiedu.ds_informationSchema.service.SchemaPrivilegesService;
import com.itmayiedu.ds_informationSchema.service.TableService;
import com.itmayiedu.service.LoadInfo;
//import com.itmayiedu.entity.Items;
//import com.itmayiedu.mapper.ItemsMapper;
//import com.itmayiedu.service.ItemsService;

@RestController  //表示修饰该Controller所有的方法返回JSON格式,直接可以编写Restful接口
public class IndexController {

	//springboot中集成log4j只需要加载log4j.properties文件即可
	private static Logger logger = Logger.getLogger(IndexController.class);
	
	/**
	 * fpq数据源的items
	 */
	@Autowired
	private ItemsServicefpq itemsServicefpq;
	
	/**
	 * informationalSchema数据源的items=mq
	 */
	@Autowired
	private ItemsServiceInfoSchema itemsServiceInfoSchema;
	
//	private ItemsService itemsServiceInfo;
	
//	@Autowired
//	private TableService tableService;
//	
//	@Autowired
//	private SchemaPrivilegesService schemaPrivilegesService;

	
	
	
	/**
	 * adItemInfoSchema:(controller调用infoSchema数据源的service方法添加item表数据). <br/> 
	 * TODO(结论：测试成功).<br/> 
	 * http://127.0.0.1:8080/adItemInfoSchema?name=水杯22&detail=1200L
	 * @author xn042142 付品欣
	 * @param name
	 * @param detail
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午2:53:29
	 */
	@Transactional  //springboot默认集成事物,设置方法adItemInfoSchema（）范围内进行事物处理
	@RequestMapping("/adItemInfoSchema")
	public String adItemInfoSchema(String name,String detail){
		System.out.println("开始调用infoSchema数据源的service方法：");
		itemsServiceInfoSchema.addItem(name, detail);
		System.out.println("infoSchema数据源添加items,name:" + name + ",detail:" + detail);
		return "infoSchema数据源添加items,name:" + name + ",detail:" + detail;
	}
	
	/**
	 * adItemfpq:(controller调用fpq数据源的service方法王fpq数据库的items中添加数据). <br/> 
	 * TODO(结论：测试成功).<br/> 
	 * http://127.0.0.1:8080/adItemfpq?name=水杯22&detail=1200L
	 * @author xn042142 付品欣
	 * @param name
	 * @param detail
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午2:54:17
	 */
	@Transactional
	@RequestMapping("/adItemfpq")
	public String adItemfpq(String name,String detail){
		System.out.println("开始调用fpq数据源的service方法：");
		itemsServicefpq.addItem(name, detail);
		System.out.println("fpq数据源添加items,name:" + name + ",detail:" + detail);
		return "fpq数据源添加items,name:" + name + ",detail:" + detail;
	}
	
	
	
	/**
	 * testAtomikos:(fpq的服务中调用infoSchema的mapper插入数据). <br/> 
	 * TODO(测试结论：ok).<br/> 
	 * http://127.0.0.1:8080/testAtomikos?name=水杯22&detail=1200L
	 * @author xn042142 付品欣
	 * @param name
	 * @param detail
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午3:05:18
	 */
	@Transactional
	@RequestMapping("/testAtomikos")
	public String testAtomikos(String name,String detail){
		System.out.println("测试使用atomikos统一管理多数据源的事物");
		itemsServicefpq.fpqAddInfoSchema(name, detail);
		return "测试使用atomikos统一管理多数据源的事物";
	}
	
	/**
	 * fpqAddInfo:(在fpq数据源服务中调用info数据源的mapper). <br/> 
	 * TODO(结论：ok).<br/> 
	 * http://127.0.0.1:8080/fpqAddInfo?name=水杯22&detail=1200L
	 * @author xn042142 付品欣
	 * @param name
	 * @param detail
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 上午11:48:22
	 */
	@Transactional
	@RequestMapping("/fpqAddInfo")
	public String fpqAddInfo(String name,String detail){
		System.out.println("测试使用atomikos,在fpq数据源服务中调用info数据源的mapper统一管理多数据源的事物");
		int ct = itemsServicefpq.fpqAddInfo(name, detail);
		String result = "";
		if(ct > 0){
			result = "测试使用atomikos,在fpq数据源服务中调用info数据源的mapper统一管理多数据源的事物-->添加成功，name=" + name;
		}else{
			result = "测试使用atomikos,在fpq数据源服务中调用info数据源的mapper统一管理多数据源的事物-->添加失败，name=" + name;
		}
		System.out.println(result);
		return result;
	}
	
	/**
	 * fpqAddInfoService:(测试使用atomikos,在fpq数据源服务中调用info数据源的服务添加items统一管理多数据源的事务). <br/> 
	 * TODO(结论：数据添加到fpq的数据源中了).<br/> 
	 * http://127.0.0.1:8080/fpqAddInfoService?name=水杯22&detail=1200L
	 * @author xn042142 付品欣
	 * @param name
	 * @param detail
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午1:44:58
	 */
	@Transactional
	@RequestMapping("/fpqAddInfoService")
	public String fpqAddInfoService(String name,String detail){
		System.out.println("测试使用atomikos,在fpq数据源服务中调用info数据源的mapper统一管理多数据源的事物");
		int ct = itemsServicefpq.fpqAddInfoService(name, detail);
		String result = "";
		if(ct > 0){
			result = "测试使用atomikos,在fpq数据源服务中调用info数据源的mapper统一管理多数据源的事物-->添加成功，name=" + name;
		}else{
			result = "测试使用atomikos,在fpq数据源服务中调用info数据源的mapper统一管理多数据源的事物-->添加失败，name=" + name;
		}
		System.out.println(result);
		return result;
	}
	
	/**
	 * infoSchemaAddFpq:(测试使用atomikos,在info数据源服务中调用fpq数据源的service统一管理多数据源的事物). <br/> 
	 * TODO(结论：数据添加到fpq的itmes表中成功).<br/> 
	 * http://127.0.0.1:8080/infoSchemaAddFpqService?name=水杯22&detail=1200L
	 * @author xn042142 付品欣
	 * @param name
	 * @param detail
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午1:48:22
	 */
	@Transactional
	@RequestMapping("/infoSchemaAddFpqService")
	public String infoSchemaAddFpqService(String name,String detail){
		System.out.println("测试使用atomikos,在info数据源服务中调用fpq数据源的service统一管理多数据源的事物-->开始，name=" + name);
		int ct = itemsServiceInfoSchema.infoAddFpqService(name, detail);
		String result = "";
		if(ct > 0){
			result = "测试使用atomikos,在info数据源服务中调用fpq数据源的service统一管理多数据源的事物-->添加成功，name=" + name;
		}else{
			result = "测试使用atomikos,在info数据源服务中调用fpq数据源的service统一管理多数据源的事物-->添加失败，name=" + name;
		}
		System.out.println(result);
		return result;
	}
	
	/**
	 * testTransaction0:(cotroller中调用fpq和infoSchema的服务分别往两个数据源的itemes中添加数据，中间不设置错误). <br/> 
	 * TODO(结论：ok,两个库中都添加进去了).<br/> 
	 * http://127.0.0.1:8080/testTransaction0?name=水杯22&detail=1200L
	 * 修改端口号后房屋url: http://127.0.0.1:8888/itmayiedu/testTransaction0?name=%E6%B0%B4%E6%9D%AF22&detail=1200L
	 * @author xn042142 付品欣
	 * @param name
	 * @param detail
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午3:20:11
	 */
	@Transactional
	@RequestMapping("/testTransaction0")
	public String testTransaction0(String name,String detail){
		System.out.println("开始调用fpq数据源的service方法：");
		//向fpq数据库中插入数据
		int count = itemsServicefpq.addItem(name, detail);
		if(count > 0){
			System.out.println("itemsServicefpq.addItem插入成功！");
		}else{
			System.out.println("itemsServicefpq.addItem插入失败！");
		}
		//添加了@Transactional标签，设置testTransaction方法为一个事务，当出差时，都不提交；
		//int error = 44/0; 
		//dataSourcefpq是primary(主的数据源）testTransaction归属于dataSourcefpq；
		//当执行到“int error = 44/0; "报错时，回滚的是dataSourcefpq数据源的事务，不会回滚DataSource_informationSchema数据源的事务，
		//因此itemsService.addItem(name, detail);插入数据库的事务不会回滚；
		//向mq数据库中插入数据
		System.out.println("开始调用infoschema数据源的service方法：");
		int count1 = itemsServiceInfoSchema.addItem(name, detail);
		if(count1 > 0){
			System.out.println("itemsServicefpq.addItem插入成功！");
		}else{
			System.out.println("itemsServicefpq.addItem插入失败！");
		}
		
		return "添加了@Transactional标签，设置testTransaction方法为一个事务，当出错时，都不提交；!";
	}
	
	/**
	 * findSchema:(controller中查询infoSchema数据源中的数据). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8080/findSchema?name=水杯22&detail=1200L
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午3:23:06
	 */
//	@RequestMapping("/findSchema")
//	public String findSchema(){
//		SchemaPrivileges  schemaPrivileges  = schemaPrivilegesService.findSchemaPrivileges();
//		return schemaPrivileges.printInfo();
//	}
	
	//测试多数据源请用url:http://127.0.0.1:8080/add?itemName=影响7&tableName=items
	/**
	 * add:(查询fqp和infoSchema两个数据源的数据). <br/> 
	 * TODO(结论：ok).<br/> 
	 * http://127.0.0.1:8080/findItemCount?itemName=笔记本
	 * @author xn042142 付品欣
	 * @param itemName
	 * @param tableName
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午3:24:22
	 */
	@RequestMapping("/findItemCount")
	public String findItemCount(String itemName){
		String result = "从fpqschame查询到的数据是：";
		int countfpq = itemsServicefpq.findItemsCount();
		result += "items表中有"+countfpq+"条记录，您查询的" + itemName + "详细信息是：";
		Items item = itemsServicefpq.findItemsByName(itemName);
		result += item.printItemInfo();
		
		result += "从informationSchema查询到的数据是：";
		System.out.println(result);
		int count = itemsServiceInfoSchema.findItemsCount();//tableService.findTableCount();
		result += "，infoSchema数据库中items表中查询到的商品" + itemName +"目前有" + count + "个。";
		System.out.println(result);
		return result;
	}
	
	/**
	 * testTransaction:(controller调用infoSchame和fpq两个数据源的方法，中间设置错误，测试事务是否回滚). <br/> 
	 * TODO(结论：ok，报错前面的事物也回滚了).<br/> 
	 * http://127.0.0.1:8080/testTransaction?name=水杯22&detail=1200L
	 * @author xn042142 付品欣
	 * @param name
	 * @param detail
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午2:57:13
	 */
	@Transactional
	@RequestMapping("/testTransaction")
	public String testTransaction(String name,String detail){
		
		//dataSourcefpq是primary(主的数据源）testTransaction归属于dataSourcefpq；
		//当执行到“int error = 44/0; "报错时，回滚的是dataSourcefpq数据源的事务，不会回滚DataSource_informationSchema数据源的事务，
		//因此itemsService.addItem(name, detail);插入数据库的事务不会回滚；
		//向mq数据库中插入数据
				int count1 = itemsServiceInfoSchema.addItem(name, detail);
				if(count1 > 0){
					System.out.println("itemsServicefpq.addItem插入成功！");
				}else{
					System.out.println("itemsServicefpq.addItem插入失败！");
				}
		
		//添加了@Transactional标签，设置testTransaction方法为一个事务，当出差时，都不提交；
		int error = 44/0; 
		//向fpq数据库中插入数据
				int count = itemsServicefpq.addItem(name, detail);
				if(count > 0){
					System.out.println("itemsServicefpq.addItem插入成功！");
				}else{
					System.out.println("itemsServicefpq.addItem插入失败！");
				}
		return "添加了@Transactional标签，设置testTransaction方法为一个事务，当出差时，都不提交；!";
	}
	

	
	
	/**
	 * index:(springBoot使用jsp页面显示数据). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8080/index
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午3:45:10
	 */
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	
	/**
	 * useLog4jPrintLog:(在springBoot中集成log4j测试). <br/> 
	 * TODO(集成方法：只需要添加lo4j.properties配置文件即可).<br/> 
	 * http://127.0.0.1:8080/useLog4jPrintLog?content=输入的测试
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午5:52:14
	 */
	@RequestMapping("/useLog4jPrintLogP")
	public String useLog4jPrintLogP(String content){
		logger.info("这是用log4j打印出来的info级别的日志。集成方法：只需要添加lo4j.properties配置文件即可.日志路径：D:\\log\\springBootLog"+"输入的内容：" + content);
		logger.error("这是用log4j打印出来的error级别的日志。集成方法：只需要添加lo4j.properties配置文件即可.日志路径：D:\\log\\springBootLog");
		logger.debug("这是用log4j打印出来的debug级别的日志。集成方法：只需要添加lo4j.properties配置文件即可.日志路径：D:\\logspringBootLog");
		return "在springBoot中集成log4j测试，集成方法：只需要添加lo4j.properties配置文件即可"+"输入的内容：" + content;
	}
	
	/**
	 * useLog4jPrintLog:(在springBoot中集成log4j测试). <br/> 
	 * TODO(集成方法：只需要添加lo4j.properties配置文件即可).<br/> 
	 * http://127.0.0.1:8080/useLog4jPrintLog
	 * @author xn042142 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2017年11月24日 下午5:52:14
	 */
	@RequestMapping("/useLog4jPrintLog")
	public String useLog4jPrintLog(){
		logger.info("这是用log4j打印出来的info级别的日志。集成方法：只需要添加lo4j.properties配置文件即可.日志路径：D:\\log\\springBootLog");
		logger.error("这是用log4j打印出来的error级别的日志。集成方法：只需要添加lo4j.properties配置文件即可.日志路径：D:\\log\\springBootLog");
		logger.debug("这是用log4j打印出来的debug级别的日志。集成方法：只需要添加lo4j.properties配置文件即可.日志路径：D:\\logspringBootLog");
		return "useLog4jPrintLog respose:在springBoot中集成log4j测试，集成方法：只需要添加lo4j.properties配置文件即可";
	}
	
	/**
	 * selectFromEHCatch:(使用EHCatch缓存). <br/> 
	 * TODO(数据在数据库中删除后，仍然可在在缓存中查询到，除非调用清楚缓存的方法deleteFromEHCatch清楚缓存).<br/> 
	 * http://127.0.0.1:8080/selectFromEHCatch?id=2
	 * @author xn042142 付品欣
	 * @param id
	 * @return 
	 * @since JDK 1.8
	 * 2017年12月8日 上午10:54:27
	 */
	@RequestMapping("/selectFromEHCatch")
	public String selectFromEHCatch(int id){
		Items item = itemsServicefpq.FindInEHCatch(id);
		return item.printItemInfo();
	}
	
	/**
	 * deleteFromEHCatch:(删除EHCatch缓存). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8080/deleteFromEHCatch
	 * @author fpq 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2017年12月8日 下午11:39:41
	 */
	@RequestMapping("/deleteFromEHCatch")
	public String deleteFromEHCatch(){
		String result = "清除EHCatch中的缓存失败！false";
		if("success" == itemsServicefpq.deleteFromEHCatch())
		{
			result = "清楚EHCatch中的缓存成功！ success";
		}
		return result;
	}
	
	/**
	 * asyncSchedul:(测试@Async异步调度). <br/> 
	 * TODO(启动加上@EnableAsync ,需要执行异步方法上加入	@Async).<br/> 
	 * http://127.0.0.1:8080/asyncSchedul?smsCount=5
	 * @author fpq 付品欣
	 * @param smsCount
	 * @return 
	 * @since JDK 1.8
	 * 2017年12月9日 上午12:12:40
	 */
	@ResponseBody
	@RequestMapping("/asyncSchedul")
	public String asyncSchedul(int smsCount){
		logger.info("***** IndexController ***** begin 1 ");
		String result = itemsServicefpq.sendSMS(smsCount);
		logger.info("***** IndexController ***** end 4 ");
		return result;
	}
	
	
	/**
	 * dateBaseInfo:(在java代码中读取properties配置文件中自定义的参数). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8080/dateBaseInfo
	 * @author fpq 付品欣
	 * @return 
	 * @since JDK 1.8
	 * 2017年12月9日 下午11:34:28
	 */
	@ResponseBody
	@RequestMapping("/dateBaseInfo")
	public String dateBaseInfo(){
		return itemsServicefpq.dateBaseInfo();
	}
	
}
