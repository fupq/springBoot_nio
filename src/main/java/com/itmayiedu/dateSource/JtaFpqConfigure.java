package com.itmayiedu.dateSource;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.itmayiedu.config.DBConfigfpq;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * 
* <p>Title:JtaFpqConfigure </p>
* <p>Description: 创建多数据源fpqDataSource</p>
* @author xn042142 付品欣
* @date 2018年1月18日 下午5:36:30
 */
@Configuration //将JtaFpqConfigure注入到spring容器中
//basePackages 最好分开配置 如果放在同一个文件夹可能会报错
@MapperScan(basePackages = {"com.itmayiedu.ds_fpq","com.itmayiedu.ds_informationSchema"}, sqlSessionTemplateRef = "fpqSqlSessionTemplate")
public class JtaFpqConfigure {
	// 配置数据源
	@Primary
	@Bean(name = "fpqDataSource")
	public DataSource fpqDataSource(DBConfigfpq fpqConfig) throws SQLException {
		//**** begin:创建数据源mysqlXaDataSource
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(fpqConfig.getUrl());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(fpqConfig.getPassword());
		mysqlXaDataSource.setUser(fpqConfig.getUsername());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		//**** end:创建数据源mysqlXaDataSource
		
		//**** begin:将数据源mysqlXaDataSource交给atomikos进行管理
		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("fpqDataSource");

		xaDataSource.setMinPoolSize(fpqConfig.getMinPoolSize());
		xaDataSource.setMaxPoolSize(fpqConfig.getMaxPoolSize());
		xaDataSource.setMaxLifetime(fpqConfig.getMaxLifetime());
		xaDataSource.setBorrowConnectionTimeout(fpqConfig.getBorrowConnectionTimeout());
		xaDataSource.setLoginTimeout(fpqConfig.getLoginTimeout());
		xaDataSource.setMaintenanceInterval(fpqConfig.getMaintenanceInterval());
		xaDataSource.setMaxIdleTime(fpqConfig.getMaxIdleTime());
		xaDataSource.setTestQuery(fpqConfig.getTestQuery());
		//**** end:将数据源mysqlXaDataSource交给atomikos进行管理
		return xaDataSource;
	}
	
	@Bean(name = "fpqSqlSessionFactory")
	public SqlSessionFactory fpqSqlSessionFactory(@Qualifier("fpqDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}

	@Bean(name = "fpqSqlSessionTemplate")
	public SqlSessionTemplate fpqSqlSessionTemplate(
			@Qualifier("fpqSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}


}
