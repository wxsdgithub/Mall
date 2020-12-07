package com.qfedu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.qfedu.domain.Goods;
import com.qfedu.domain.GoodsType;

/**
 * ��Ʒ����Dao
 * @author 18741
 *
 */
public interface GoodsTypeDao {
	//����
	@Insert("insert into t_goodstype(name,level,parentName,flag)"
			+ "values(#{name},#{level},#{parentName},1)")
	int save(GoodsType gt);
	
	//��ѯһ����Ʒ
	@Select("select * from t_goodstype where level=1")
	@ResultType(GoodsType.class)
	List<GoodsType> queryByLevel();
	
	//��ѯȫ����Ʒ
	@Select("select * from t_goodstype")
	@ResultType(GoodsType.class)
	List<GoodsType> queryAll();
	
	//������Ʒ�ȼ������Ʋ�ѯ��Ʒ��admin)
	@Select("select * from t_goodstype where level=#{flag} and name=#{name}")
	@ResultType(GoodsType.class)
	List<GoodsType> queryNameAndFlag(@Param("name")String name,@Param("flag")int flag);
	
	//����idɾ����Ʒ����
	@Delete("delete from t_goodstype where id=#{id}")
	int deleteType(@Param("id")int tid);
	
}
