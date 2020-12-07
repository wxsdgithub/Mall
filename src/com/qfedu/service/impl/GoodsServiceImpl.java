package com.qfedu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfedu.dao.GoodsDao;
import com.qfedu.domain.Goods;
import com.qfedu.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {
	
	@Autowired
	private GoodsDao goodsDao;;
	
	@Override
	public boolean save(Goods goods) {
		return goodsDao.save(goods)>0;
	}

	@Override
	public List<Goods> queryAll() {
		return goodsDao.queryAll();
	}

	@Override
	public List<Goods> queryByName(String name) {
		return goodsDao.queryName(name);
	}

	@Override
	public List<Goods> queryByType(String type) {
		return goodsDao.queryByType(type);
	}

	@Override
	public Goods querySingle(int id) {
		return goodsDao.querySingle(id);
	}

	//查找所有的以商品类型区别的所有商品
	@Override
	public List<List<Goods>> queryIndex() {
		List<List<Goods>> list = new ArrayList<List<Goods>>();
		list.add(goodsDao.queryIndex("酒水饮料"));
		list.add(goodsDao.queryIndex("饼干糕点"));
		list.add(goodsDao.queryIndex("休闲零食"));
		return list;
	}

	@Override
	public List<Goods> queryNameAndPub(String name, String pubdate) {
		return goodsDao.queryNameAndPub(name, pubdate);
	}

	@Override
	public int deleteById(int id) {
		return goodsDao.deleteById(id);
	}

}
