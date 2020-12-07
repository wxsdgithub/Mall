package com.qfedu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.qfedu.domain.Goods;

/**
 * ��Ʒ��Dao
 * @author 18741
 *
 */
public interface GoodsDao {
	//����
	@Insert("insert into t_goods(name,price,pubdate,typeName,intro,picture,flag,star)"
			+ "values(#{name},#{price},#{pubdate},#{typeName},#{intro},#{picture},1,#{star})")
	public int save(Goods goods);
	
	//��ѯ����
	@Select("select * from t_goods")
	@ResultType(Goods.class)
	public List<Goods> queryAll();
	
	//��ѯ����
	@Select("select * from t_goods where id=#{id}")
	@ResultType(Goods.class)
	public Goods querySingle(int id);
	
	//������Ʒ���Ͳ�ѯ,flag=1��ʾ����Ʒ�����ϼ���
	@Select("select * from t_goods where flag=1 and typeName=#{type}")
	@ResultType(Goods.class)
	public List<Goods> queryByType(String type); 
	
	//��ѯ��ҳ����Ʒ��Ϣ
	@Select("select * from t_goods where typename=#{type} order by star desc limit 5")
	@ResultType(Goods.class)
	public List<Goods> queryIndex(String type);
	
	//������Ʒ��ģ����ѯ
	@Select("select * from t_goods where name LIKE CONCAT('%',#{name},'%')")
	@ResultType(Goods.class)
	public List<Goods> queryName(String name);
	
	//������Ʒ���ƺ��ϼ�ʱ���ѯ
	@Select("<script>"+
	"select * from t_goods"+
			"<where>"+
			"<if text='name!=null'>"+
			"and name LIKE COMCAT('%',#{name},'%')"+
			"</if>"+
			"<if text='pubdate!=null'>"+
			"and pubdate=#{pubdate}"+
			"</if>"+
			"</where>"+
			"</script>"
			)
	@ResultType(Goods.class)
	public List<Goods> queryNameAndPub(@Param("name")String name,@Param("pubdate")String pubdate);
	
	//ɾ����Ʒ
	@Delete("delete t_goods where id=#{id}")
	public int deleteById(int id);
	
}
