package com.itmayiedu.app;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.itmayiedu.config.DBConfigInfoSchema;
import com.itmayiedu.config.DBConfigfpq;
import com.itmayiedu.controller.IndexController;
import com.itmayiedu.ds_fpq.service.ItemsServicefpq;
import com.itmayiedu.service.LoadInfo;
//设置控制器扫包范围
@ComponentScan(basePackages={"com.encrypt.useEncryptJar","common.webCommon","com.quartz","com.itmayiedu.app","com.itmayiedu.config","com.encrypt","com.itmayiedu.controller","com.itmayiedu.dateSource","com.itmayiedu.ds_fpq","com.itmayiedu.ds_informationSchema"})
@MapperScan(basePackages={"com.itmayiedu.mapper","com.itmayiedu.ds_fpq.mapper","com.itmayiedu.ds_informationSchema.mapper"})//
@EnableAutoConfiguration  //让 Spring Boot   根据应用所声明的依赖来对 Spring 框架进行自动配置.这个注解告诉Spring Boot根据添加的jar依赖猜测你想如何配置Spring。
@EnableCaching //开启ehcatch缓存注解
@EnableScheduling //开启定时任务注解
//@EnableAsync //开启异步（启动子线程），开启时就报错，找不到原因20171209
@EnableConfigurationProperties(value={DBConfigfpq.class,DBConfigInfoSchema.class}) //该标签实现通过‘DBConfigfpq.class,DBConfigInfoSchema.class’读取配置文件中的内容
public class App {
	
	private static Logger logger = Logger.getLogger(App.class);
		
	public static void main(String[] args){
		logger.info("*************************************** Spring Boot 准备启动  *****************************");
		//标识App.class为启动类
		SpringApplication.run(App.class, args);
		logger.info("*************************************** Spring Boot 启动完毕  *****************************");
		logger.info("可在application.properties配置文件中修改端口号及上下文；");
	}
	
	@Bean
    public HttpMessageConverters fastJsonConverters(){
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastConf = new FastJsonConfig();
        
        fastConf.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConverter.setFastJsonConfig(fastConf);
        
        HttpMessageConverter<?> converter = fastJsonConverter;
        return new HttpMessageConverters(converter);    
    }
	
}
