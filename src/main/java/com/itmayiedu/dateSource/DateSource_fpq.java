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
//* <p>Title:DateSource_fpq </p>
//* <p>操作数据源：mqsql数据库fpq账户下的表 </p>
//* @author xn042142 付品欣
//* @date 2017年11月16日 下午9:00:58
// */
//@Configuration  //把扫描到的包注入都spring容器中
//@MapperScan(basePackages="com.itmayiedu.ds_fpq",sqlSessionFactoryRef="fpqSqlSessionFactory")
//public class DateSource_fpq {
//	/**
//	 * testDataSource:(创建fpq数据源). <br/> 
//	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
//	 * 
//	 * @author xn042142 付品欣
//	 * @return 
//	 * @since JDK 1.8
//	 * 2017年11月16日 下午9:12:54
//	 */
//	@Bean(name = "fpqDataSource") //配置bean"fpqDataSource"到spring容器中
//	@ConfigurationProperties(prefix = "spring.datasource.fpq") //加载数据源“spring.datasource.fpq”的配置
//	@Primary
//	public DataSource fpqDataSource() {
//		System.out.println("初始化‘fpqDataSource’数据源");
//		return DataSourceBuilder.create().build();  //创建数据源
//	}
//
//	/**
//	 * testSqlSessionFactory:(fpq 创建sql会话工厂). <br/> 
//	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
//	 * 
//	 * @author xn042142 付品欣
//	 * @param dataSource 数据源
//	 * @return
//	 * @throws Exception 
//	 * @since JDK 1.8
//	 * 2017年11月16日 下午9:14:21
//	 */
//	@Bean(name = "fpqSqlSessionFactory") //配置sqlSessionFactory的bean"fpqSqlSessionFactory"到spring容器中
//	@Primary
//	public SqlSessionFactory fpqSqlSessionFactory(@Qualifier("fpqDataSource") DataSource dataSource) //此sqlSessionFactory使用的是fpqDataSource的数据源
//			throws Exception {
//		System.out.println("初始化‘fpqSqlSessionFactory’的连接工厂");
//		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//		bean.setDataSource(dataSource);
////		bean.setMapperLocations( //配置查询分页和加载配置文件
////				new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
//		return bean.getObject();
//	}
//
//	/**
//	 * testTransactionManager:(创建fpq事务管理器). <br/> 
//	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
//	 * @author xn042142 付品欣
//	 * @param dataSource
//	 * @return 
//	 * @since JDK 1.8
//	 * 2017年11月16日 下午9:15:23
//	 */
//	@Bean(name = "fpqTransactionManager") //将事物管理器的bean "fpqTransactionManager"注入到spring容器中
//	@Primary
//	public DataSourceTransactionManager fpqTransactionManager(@Qualifier("fpqDataSource") DataSource dataSource) { //此事务管理器处理的数据源是fpqDataSource
//		System.out.println("初始化‘fpqTransactionManager’的事务管理器");
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
//	@Bean(name = "fpqSqlSessionTemplate") //将sqlSession的模板“fpqSqlSessionTemplate”注入到spring容器中
//	public SqlSessionTemplate fpqSqlSessionTemplate(
//			@Qualifier("fpqSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception { //sqlSession模板使用的sqlSession工厂是fpqSqlSessionFactory
//		System.out.println("初始化‘fpqSqlSessionTemplate’的session模板");
//		return new SqlSessionTemplate(sqlSessionFactory);
//	}
//
//}
