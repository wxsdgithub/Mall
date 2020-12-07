package com.qfedu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.common.vo.ViewOrder;
import com.qfedu.dao.CartDao;
import com.qfedu.dao.OrderDao;
import com.qfedu.domain.CartDetail;
import com.qfedu.domain.Order;
import com.qfedu.domain.OrderDetail;
import com.qfedu.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private CartDao cartDao;
	
	//下单（把指定用户的购车里的所有商品当作一个订单进行下单)
	@Override
	public boolean save(String oid, int uid, int uaid) {
		//通过用户id查找出购物车里所有的商品,因为该功能是从购物车下单的，不能用oid查询商品
		List<CartDetail> cds = cartDao.queryByDetail(uid);
		double sum=0;
		
		//创建一个订单详情（子项），包含很多的商品信息
		for(int i=0; i < cds.size(); i++){
			OrderDetail detail = new OrderDetail();
			detail.setOid(oid);
			detail.setGid(cds.get(i).getGid());
			detail.setNum(cds.get(i).getNum());
			//这里为什么要乘上100？
			detail.setMoney(cds.get(i).getMoney()*100);
			//把购物车里的商品都放入订单详情里
			orderDao.insertDetail(detail);
			sum+=cds.get(i).getMoney();
		}
		
		//创建一个订单，该订单包含上面的订单详情
		Order order = new Order();
		order.setId(oid);
		order.setUaid(uaid);
		order.setUid(uid);
		order.setMoney(sum);
		orderDao.insert(order);
		
		//提交订单的同时需要把购物车里的东西给清掉
		//1.清掉用户专属购物车的钱（购物车不能清掉，因为只要创建用户就会自动触发创建一个购物车id）
		cartDao.updateEmpty(uid);
		//2.清掉用户购物车里的所有商品
		cartDao.deletecartDetailByCid(cds.get(0).getCid());
		return true;
	}
	
	//直接下单，就是对用户的有一个指定的订单里的一件商品进行下单
	@Override
	public boolean insertDirect(int uid, String oid, int uaid, CartDetail cd) {
		
		if(cd == null) return false;
		
		//创建一个用户自己创建的订单
		Order order = new Order();
		order.setUid(uid);
		order.setId(oid);
		order.setUaid(uaid);
		order.setMoney(cd.getMoney());
		orderDao.insert(order);
		
		//创建一个订单详情
		OrderDetail detail = new OrderDetail();
		detail.setGid(cd.getGid());
		detail.setOid(oid);
		detail.setNum(cd.getNum());
		detail.setMoney(cd.getMoney());
		orderDao.insertDetail(detail);
		return true;
	}
	
	//更改订单状态
	@Override
	public boolean update(String oid, int flag) {
		return orderDao.update(oid, flag)>0;
	}

	//查询用户的所有订单
	@Override
	public List<Order> queryByUid(int uid) {
		return orderDao.queryByUid(uid);
	}

	//查询订单详情	
	@Override
	public ViewOrder queryOrderdetailById(String oid) {
		ViewOrder vo = orderDao.queryOrder(oid);
		vo.setList(orderDao.queryDetailList(oid));
		return vo;
	}
	
	//查询所有订单
	@Override
	public List<Order> queryAll() {
		return orderDao.queryAll();
	}
	
	@Override
	public List<Order> selectByNameAndFlag(String username, Integer flag) {
		return orderDao.selectByNameAndFlag(username, flag);
	}
	
	//删除订单
	@Override
	public int deleteById(String id) {
		return orderDao.deleteById(id);
	}

}
