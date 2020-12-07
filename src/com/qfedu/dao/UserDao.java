package com.qfedu.dao;

/**
 * 用户的Dao
 */
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.qfedu.domain.User;

public interface UserDao {
	
	
	//新增用户
	@Insert("insert into t_user(role,username,password,email,gender,createtime,"
			+ "flag,activatecode) values(1,#{username},#{password},#{email},#{gender},now(),"
			+ "1,#{activatecode})")
	int insert(User user);
	
	//通过用户名查找用户，但是记得查找得用户得flag必须要为2，因为这是代表有效，反之如果为1，则是未激活
	@Select("select * from t_user where flag=2 and (username=#{username} or email=#{email})")
	@ResultType(User.class)
	User select(String name);
	
	//注册时查看是否已经存在该用户名了
	@Select("select count(*) from t_user where username=#{username}")
	int checkName(String username);
	
	//注册时查看是否已经存在该邮箱了
	@Select("select count(*) from t_user where email=#{email}")
	int checkEmail(String email);
	
	//查找所有用户
	@Select("select * from t_user where flag=2")
	@ResultType(User.class)
	List<User> selectAll();
	
	//激活用户，本质上就是将状态flag改为2
	@Update("update t_user set flag=2 where email=#{email} and activatecode=#{code}")
	int updateAcode(@Param("email")String email,@Param("code")String code);
	
	//删除用户
	@Delete("delete from t_user where id=#{id}")
	int deleteById(int id);
	
	//使用搜索模糊查询用户
	@Select("select * from t_user where username like CONCAT('%',#{username},'%') "
			+ "and gender=#{gender}")
	@ResultType(User.class)
	List<User> selectsearch(@Param("username")String username,@Param("gender")String gender);
	
}
