package com.qfedu.service;

import java.util.List;

import com.qfedu.common.vo.ViewCart;
import com.qfedu.domain.Cart;
import com.qfedu.domain.Goods;

public interface CartService {
		// 创建购物车
		public boolean createCart(Cart cart);
		// 加入购物车 详情页
		public boolean add(int cid, Goods gds, int num);
		// 修改数量 购物车页面
		public boolean changeNum(int cid, Goods gds, int num);
		// 获取购物车对象
		public Cart queryByUid(int uid);
		// 购物车的数据
		public List<ViewCart> queryCart(int cid);
		// 删除购物车的商品
		public int deleteDetail(int cid,int gid);
}
