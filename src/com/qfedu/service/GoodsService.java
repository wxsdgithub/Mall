package com.qfedu.service;

import java.util.List;

import com.qfedu.domain.Goods;

public interface GoodsService {
		// ����
		boolean save(Goods goods);
		// ��ѯ
		List<Goods> queryAll();
		//ģ����ѯ
		List<Goods> queryByName(String name);
		//������Ʒ���Ͳ�ѯ
		List<Goods> queryByType(String type);
		// ��ѯ����
		Goods querySingle(int id);
		List<List<Goods>> queryIndex();
		//������Ʒ���ƺ��ϼ�ʱ���ѯ��Ʒ��admin��
		List<Goods> queryNameAndPub(String name,String pubdate);
		//ɾ����Ʒ
		int deleteById(int id);
}
