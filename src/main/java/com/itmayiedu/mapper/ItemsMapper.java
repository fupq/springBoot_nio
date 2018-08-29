//package com.itmayiedu.mapper;
//
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
//
//import com.itmayiedu.entity.Items;
//
//public interface ItemsMapper {
//
//	@Select("SELECT * FROM fpq.items t WHERE t.name = #{name}")
//	public Items findItem(@Param("name") String name);
//	
//	@Select("SELECT count(*) FROM fpq.items")
//	public Integer findItemCount();
//	
//	@Insert("INSERT  INTO `items`(`name`,`price`,`detail`,`pic`,`createtime`) VALUES (#{name},3000.0,#{detail},NULL,'2015-02-03 13:22:53')")
//	public int addItems(@Param("name") String name,@Param("detail") String detail);
//}
