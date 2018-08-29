//package com.itmayiedu.dateSource;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
///**
// * 
//* <p>Title:DataSource_informationSchema </p>
//* <p>Description: 操作数据源：mqsql数据库informationSchema账户下的表</p>
//* @author xn042142 付品欣
//* @date 2017年11月16日 下午9:40:36
// */
//@Configuration  //把扫描到的包注入都spring容器中
//@MapperScan(basePackages="com.itmayiedu.ds_informationSchema",sqlSessionFactoryRef="informationSchemaSqlSessionFactory")
//public class DataSource_informationSchema {
//	/**
//	 * testDataSource:(创建informationSchema数据源). <br/> 
//	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
//	 * 
//	 * @author xn042142 付品欣
//	 * @return 
//	 * @since JDK 1.8
//	 * 2017年11月16日 下午9:12:54
//	 */
//	@Bean(name = "informationSchemaDataSource") //配置bean"informationSchemaDataSource"到spring容器中
//	@ConfigurationProperties(prefix = "spring.datasource.informationSchema") //加载数据源“spring.datasource.informationSchema”的配置
//	public DataSource informationSchemaDataSource() {
//		System.out.println("初始化‘informationSchemaDataSource’数据源");
//		return DataSourceBuilder.create().build();  //创建数据源
//	}
//
//	/**
//	 * testSqlSessionFactory:(创建数据源informationSchemaDataSource的sql会话工厂). <br/> 
//	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
//	 * 
//	 * @author xn042142 付品欣
//	 * @param dataSource 数据源
//	 * @return
//	 * @throws Exception 
//	 * @since JDK 1.8
//	 * 2017年11月16日 下午9:14:21
//	 */
//	@Bean(name = "informationSchemaSqlSessionFactory") //配置sqlSessionFactory的bean"informationSchemaSqlSessionFactory"到spring容器中
//	public SqlSessionFactory informationSchemaSqlSessionFactory(@Qualifier("informationSchemaDataSource") DataSource dataSource) //此sqlSessionFactory使用的是informationSchemaSqlSessionFactory的数据源
//			throws Exception {
//		System.out.println("初始化‘informationSchemaSqlSessionFactory’的连接工厂");
//		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//		bean.setDataSource(dataSource);
////		bean.setMapperLocations( //配置查询分页和加载配置文件
////				new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
//		return bean.getObject();
//	}
//
//	/**
//	 * testTransactionManager:(创建informationSchema事务管理器). <br/> 
//	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
//	 * @author xn042142 付品欣
//	 * @param dataSource
//	 * @return 
//	 * @since JDK 1.8
//	 * 2017年11月16日 下午9:15:23
//	 */
//	@Bean(name = "informationSchemaTransactionManager") //将事物管理器的bean "informationSchemaTransactionManager"注入到spring容器中
//	public DataSourceTransactionManager informationSchemaTransactionManager(@Qualifier("informationSchemaDataSource") DataSource dataSource) { //此事务管理器处理的数据源是fpqDataSource
//		System.out.println("初始化‘informationSchemaTransactionManager’的事务管理器");
//		return new DataSourceTransactionManager(dataSource);
//	}
//
//	/**
//	 * testSqlSessionTemplate:(设置sqlSession模板). <br/> 
//	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
//	 * 
//	 * @author xn042142 付品欣
//	 * @param sqlSessionFactory
//	 * @return
//	 * @throws Exception 
//	 * @since JDK 1.8
//	 * 2017年11月16日 下午9:32:00
//	 */
//	@Bean(name = "informationSchemaSqlSessionTemplate") //将sqlSession的模板“fpqSqlSessionTemplate”注入到spring容器中
//	public SqlSessionTemplate informationSchemaSqlSessionTemplate(
//			@Qualifier("informationSchemaSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception { //sqlSession模板使用的sqlSession工厂是informationSchemaSqlSessionFactory
//		System.out.println("初始化‘informationSchemaSqlSessionTemplate’的session模板");
//		return new SqlSessionTemplate(sqlSessionFactory);
//	}
//}
