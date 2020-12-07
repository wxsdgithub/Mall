package com.qfedu.service;

import java.util.List;

import com.qfedu.domain.GoodsType;

/**
 * ��ƷService
 * @author 18741
 *
 */
public interface GoodsTypeService {
		//����
		boolean save(GoodsType gt);
		//��ѯһ������
		List<GoodsType> queryByLevel();
		//��ѯȫ��
		List<GoodsType> queryAll();
		//ɾ��
		int deleteType(int tid);
		//������Ʒ�ȼ�����Ʒ���Ʋ�ѯ���ͣ�admin��
		List<GoodsType> queryNameAndFlag(String name,int flag);
}
