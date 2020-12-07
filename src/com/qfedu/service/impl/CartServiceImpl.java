package com.qfedu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.common.vo.ViewCart;
import com.qfedu.dao.CartDao;
import com.qfedu.domain.Cart;
import com.qfedu.domain.CartDetail;
import com.qfedu.domain.Goods;
import com.qfedu.service.CartService;
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDao cartDao;
	
	//新增购物车
	@Override
	public boolean createCart(Cart cart) {
		return cartDao.insert(cart)>0;
	}

	//向购物车详情里添加商品，不过要先判断，看是否为第一次加入,因为购物车可以添加相同的商品
	@Override
	public boolean add(int cid, Goods gds, int num) {
		
		//如果没有查出购物车id，即此时用户没有登录，所以没有session
		
		CartDetail detail = cartDao.queryByCid(cid, gds.getId());
		if(detail == null){
			//第一次加入该商品
			CartDetail cd = new CartDetail();
			cd.setCid(cid);
			cd.setGid(gds.getId());
			cd.setNum(num);
			cd.setMoney(num * gds.getPrice());
			return cartDao.insertDetail(cd)>0;
		}else{
			//已经添加过该商品了，继续添加(按照书上的意思是在已有的商品基础上重新添加一件商品)
			detail.setNum(detail.getNum()+1);
			detail.setMoney(detail.getMoney()+gds.getPrice());
			cartDao.deletecartDetailByCidAndGid(detail.getCid(), detail.getGid());
			return cartDao.insertDetail(detail)>0;
			
		}
	}

	//改变购物车里指定商品的数量
	@Override
	public boolean changeNum(int cid, Goods gds, int num) {
		CartDetail detail = cartDao.queryByCid(cid, gds.getId());
		
		if(num == -1){
			//num的值为-1表示当前是减少商品操作，默认都是-1
			//所以下面的商品价格要变为负数，更好给后面进行相关操作
			gds.setPrice(-gds.getPrice());
			detail.setNum(detail.getNum()-1);
		}else{
			//num的值不为-1时表明该操作时添加商品数量，默认都是加1
			detail.setNum(detail.getNum()+1);
		}
		detail.setMoney(detail.getMoney()+gds.getPrice());
		return cartDao.updateDetail(detail)>0;
	}
	
	//查找用户的购物车，本质上是为了获得用户与自身的购物车编号（cid)更好确定购物车详情里指定用户
	//的购物车id
	@Override
	public Cart queryByUid(int uid) {
		return cartDao.queryByUid(uid);
	}
	
	//详情查询，除了可以看到用户商品里的购物车商品的数量还可以看到商品的价格（Goods的相关属性)
	@Override
	public List<ViewCart> queryCart(int cid) {
		return cartDao.querycart(cid);
	}
	
	//删掉用户购物车里的某件商品
	@Override
	public int deleteDetail(int cid, int gid) {
		if(gid == 0){
			//gid==0表示清空购物车
			return cartDao.deletecartDetailByCid(cid);
		}else{
			//删除指定商品
			return cartDao.deletecartDetailByCidAndGid(cid, gid);
		}
	}

}
