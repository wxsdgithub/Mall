package com.qfedu.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.common.utils.Base64Utils;
import com.qfedu.common.utils.MD5Utils;
import com.qfedu.common.utils.StrUtils;
import com.qfedu.dao.UserDao;
import com.qfedu.domain.User;
import com.qfedu.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean save(User user) {
		//修改密码
		user.setPassword(MD5Utils.md5(user.getPassword()));
		return userDao.insert(user)>0;
	}
	
	//通过用户名查询用户
	@Override
	public User getUserByName(String name) {
		return userDao.select(name);
	}
	
	//获取所有用户
	@Override
	public List<User> selectAll() {
		return userDao.selectAll();
	}
	
	//检查用户名，true表示存在，false表示不存在
	@Override
	public boolean checkName(String name) {
		return userDao.checkName(name)>0;
	}
	
	//检查邮箱，true表示存在，false表示不存在
	@Override
	public boolean checkEmail(String email) {
		return userDao.checkEmail(email)>0;
	}
	
	//检查登录，true表示存在，false表示不存在
	@Override
	public boolean checkLogin(String name) {
		return userDao.select(name) != null;
	}
	
	//激活激活码
	@Override
	public boolean activateUser(String email, String code) {
		if(!StrUtils.empty(email,code)){
			return userDao.updateAcode(Base64Utils.decode(email),
					Base64Utils.decode(code))>0;
			
		}else{
			return false;
		}
	}

	//删除用户
	@Override
	public int deleteByid(int id) {
		return userDao.deleteById(id);
	}

	//搜索用户（通过用户名和性别）
	@Override
	public List<User> usersearch(String username, String gender) {
		return userDao.selectsearch(username, gender);
	}

}
