package com.qfedu.service;

import java.util.List;

import com.qfedu.common.vo.ViewOrder;
import com.qfedu.domain.CartDetail;
import com.qfedu.domain.Order;

public interface OrderService {
	//下单（把指定用户的购车里的所有商品当作一个订单进行下单)
	public boolean save(String oid,int uid,int uaid);
	
	//直接下单(把购物车里的某件商品直接放入订单中)
	public boolean insertDirect(int uid,String oid,int uaid,CartDetail cd);
	
	//修改订单状态
	public boolean update(String oid,int flag);
	
	//查询订单列表（指的是某个用户的所有订单列表）
	public List<Order> queryByUid(int uid);
	
	//查询订单详情
	public ViewOrder queryOrderdetailById(String oid);
	
	//查询全部订单
	public List<Order> queryAll();
	
	//根据用户姓名和订单的支付状态查询订单（admin)
	public List<Order> selectByNameAndFlag(String username,Integer flag);
	
	//删除订单
	public int deleteById(String id);
	
}
