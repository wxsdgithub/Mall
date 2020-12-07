package com.qfedu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.qfedu.domain.UserAddress;

public interface UserAddressDao {
	//新增用户地址
	@Insert("insert into t_useraddress(name,phone,detail,uid,flag)"
			+ "values(#{name},#{phone},#{detail},#{uid},1)")
	public int insert(UserAddress ua);
	
	//修改用户地址相关信息，包括电话号码，具体地址
	@Update("update t_useraddress set name=#{name},phone=#{phone},"
			+ "detail=#{detail} where id=#{id}")
	public int update(UserAddress ua);
	
	//修改状态(主要是为了修改默认地址),flag=3表示默认地址，1表示普通状态
	@Update("update t_useraddress set flag=#{flag} where id=#{id}")
	public int updateDea(@Param("id")int id,@Param("flag")int flag);
	
	//查询地址
	@Select("select * from t_useraddress where uid=#{uid} order by flag desc")
	@ResultType(UserAddress.class)
	public List<UserAddress> queryById(int uid);
	
	//删除地址信息
	@Delete("delete from t_useraddress where id=#{id}")
	int deleteById(int id);
}
