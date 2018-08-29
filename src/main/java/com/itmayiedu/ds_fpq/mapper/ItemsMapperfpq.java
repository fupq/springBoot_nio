package com.itmayiedu.ds_fpq.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

import com.itmayiedu.ds_fpq.entity.Items;
/**
 * 
* <p>Title:ItemsMapperfpq </p>
* <p>Description:springboot整合使用mybatis </p>
* @author xn042142 付品欣
* @date 2018年1月18日 下午5:14:46
 */
@CacheConfig(cacheNames = "baseCache")
public interface ItemsMapperfpq {

	@Select("SELECT * FROM fpq.items t WHERE t.name = '音响7'")
	public Items findItem();
	
	@Select("SELECT * FROM fpq.items t WHERE t.name = #{name}")
	public Items findItemByName(@Param("name") String name);
	
	@Select("SELECT count(*) FROM fpq.items")
	public Integer findItemCount();
	
	@Insert("INSERT  INTO fpq.items(name,price,detail,pic,createtime) VALUES (#{name},3000.0,#{detail},NULL,'2015-02-03 13:22:53')")
	public int addItems(@Param("name") String name,@Param("detail") String detail);
	
	/**
	 * findInEhcatch:(使用EHcatch缓存数据). <br/> 
	 * TODO(注意：1.在app.class中药开启缓存@EnableCaching；2.在mapper接口前要添加注解@CacheConfig(cacheNames = "baseCache")指定ehactch的缓存名称；3.在方法前要添加@Cacheable标识使用缓存).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param id
	 * @return 
	 * @since JDK 1.8
	 * 2017年12月8日 上午10:26:44
	 */
	@Select("SELECT * FROM fpq.items t WHERE t.id = #{id}")
	@Cacheable //该查询语句使用缓存
	public Items findInEhcatch(@Param("id") Integer id);
}
