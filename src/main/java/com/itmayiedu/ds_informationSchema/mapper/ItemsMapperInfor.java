package com.itmayiedu.ds_informationSchema.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.itmayiedu.ds_fpq.entity.Items;

public interface ItemsMapperInfor {

	@Select("SELECT * FROM mq.items t WHERE t.name = '音响7'")
	public Items findItem();
	
	@Select("SELECT * FROM mq.items t WHERE t.name = #{name}")
	public Items findItemByName(@Param("name") String name);
	
	@Select("SELECT count(*) FROM mq.items")
	public Integer findItemCount();
	
	@Insert("INSERT  INTO mq.items(name,price,detail,pic,createtime) VALUES (#{name},3000.0,#{detail},NULL,'2015-02-03 13:22:53')")
	public int addItems(@Param("name") String name,@Param("detail") String detail);
}
