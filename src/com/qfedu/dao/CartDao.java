package com.qfedu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.qfedu.common.vo.ViewCart;
import com.qfedu.domain.Cart;
import com.qfedu.domain.CartDetail;
import com.qfedu.domain.Goods;
import com.sun.org.glassfish.gmbal.ParameterNames;

/**
 * 购物车以及购物车详情一起放在一个Dao里
 * @author 18741
 *
 */
public interface CartDao {
	
	//Cart表(就是用来对应一个用户一个购物车)
	//CartDetail表(本质上是把所有用户购物车的商品都放在该表中，通过cid（购物车编号)来区分是哪个用户的
	
	//新增-创建购物车
	@Insert("insert into t_cart(uid,money) values(#{id},0)")
	public int insert(Cart cart);
	
	//购物车添加商品
	@Insert("insert into t_cartdetail(cid,gid,num,money)"
			+ " values(#{cid},#{gid},#{num},#{money})")
	public int insertDetail(CartDetail cd);
	
	//修改购物车商品(money)
	@Update("update t_cart set money=#{money} where id=#{id}")
	public int update(Cart cd);
	
	//修改购物车中的数量
	@Update("update t_cartdetail set num=#{num},money=#{money} where"
			+ " cid=#{cid} and gid=#{gid}")
	public int updateDetail(CartDetail cartDetail);
	
	//清空购物车1,就是把某个用户的购物车里的钱全部清零
	@Update("update t_cart set money=0 where id=#{id}")
	public int updateEmpty(int id);
	

	//清空购物车2,就是把某个用户购物车里的东西全部清空
	@Delete("delete from t_cartdetail where cid=#{cid}")
	public int deletecartDetailByCid(int cid);
	
	//删除用户购物车里指定商品
	@Delete("delete from t_cartdetail where cid=#{cid} and  gid=#{gid}")
	public int deletecartDetailByCidAndGid(@Param("cid")int cid,@Param("gid")int gid);
	
	//获取用户的购物车，本质上是为了获得用户与自身的购物车编号（cid)更好确定购物车详情里指定用户
	//的购物车id
	@Select("select * from t_cart where uid=#{uid}")
	@ResultType(Cart.class)
	public Cart queryByUid(int uid);
	
	//获取用户的购物车详情
	@Select("select cd.* from t_cartdetail cd left join "
			+ "t_cart c on cd.cid=c.id where c.uid=#{uid}")
	@ResultType(CartDetail.class)
	public List<CartDetail> queryByDetail(int uid);
	
	//获取详情对象，即获取用户购物车里的某件商品的详细信息
	@Select("select * from t_cartdetail where cid=#{cid} and gid=#{gid}")
	@ResultType(CartDetail.class)
	public CartDetail queryByCid(@Param("cid")int cid,@Param("gid")int gid);
	
	//获取购物车的数据,上面的详情对象也只有数量，总额，要想获得名称，单价必须要两张表联立
	//所以最终的对象是视图是最好的，即要新建一个domain包下的ViewCart类
	@Select("select cd.num,cd.money,cd.gid,g.name,g.price,g.picture from "
			+ "t_cartdetail cd left join t_goods g on cd.gid=g.id where cd.cid=#{cid}")
	@ResultType(ViewCart.class)
	public List<ViewCart> querycart(int cid);
	
}
