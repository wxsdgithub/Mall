package com.qfedu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.UserAddressDao;
import com.qfedu.domain.UserAddress;
import com.qfedu.service.UserAddressService;

@Service
public class UserAddressServiceImpl implements UserAddressService {

	@Autowired
	private UserAddressDao dao;
	
	//新增
	@Override
	public boolean insert(UserAddress ua) {
		return dao.insert(ua)>0;
	}

	//修改
	@Override
	public boolean update(UserAddress ua) {
		return dao.update(ua)>0;
	}
	
	//查询
	@Override
	public List<UserAddress> queryByUid(int uid) {
		return dao.queryById(uid);
	}
	
	//删除地址信息
	@Override
	public int deleteAddress(int id) {
		return dao.deleteById(id);
	}
	
	//修改默认信息,即将flag改为3
	@Override
	public int updteDea(int id, int uid) {
		//默认地址只要一个，所以该其中一个为默认地址时，就需要遍历找出之前的默认地址
		//将它改为非默认
		List<UserAddress> queryByUid = dao.queryById(uid);
		if(queryByUid != null && !queryByUid.isEmpty()){
			for(UserAddress userAddress:queryByUid){
				if(userAddress.getFlag() == 3){
					dao.updateDea(userAddress.getId(), 1);
				}
			}
		}
		return dao.updateDea(id, 3);
	}

}
