package com.qfedu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.qfedu.domain.Order;
import com.qfedu.domain.OrderDetail;
import com.qfedu.common.vo.ViewOrder;
import com.qfedu.common.vo.ViewOrderDetail;

public interface OrderDao {
	//新增订单详情，主要是针对用户自己添加了新的商品
	@Insert("insert into t_orderdetail(oid,gid,money,num)"
			+ "values(#{oid},#{gid},#{money},#{num})")
	public int insertDetail(OrderDetail detail);
	
	//新增订单，主要是指用户新建了一个新的订单
	//mysql语句里的now()表示该语句创建的时间，其实就是订单的创始时间
	@Insert("insert into t_order(id,uid,uaid,money,createtime,flag)"
			+ "values(#{id},#{uid},#{uaid},#{money},now(),1)")
	public int insert(Order order);
	
	//修改订单状态（包括已支付，未支付，派送中）
	@Update("update t_order set flag=#{flag} where id=#{id}")
	public int update(@Param("id")String oid,@Param("flag")int flag);
	
	//查询订单列表,即通过用户的id查找该用户所创建的所有订单，另外还要注意：
	//订单列表详情还包括订单的用户名称，电话号码，地址，所以需要订单表和地址表连接
	@Select("select o.*,CONCAT(ua.name,'-',ua.phone,'-',ua.detail) from t_order o left join "
			+ "t_useraddress ua on o.uaid=ua.id where o.uid=#{uid};")
	@ResultType(Order.class)
	public List<Order> queryByUid(int uid);
	
	//查询某个确定的订单，即通过订单id,数据库返回的每条记录是两张表连接的，所以需要用视图来接
	@Select("select * from t_order o left join t_useraddress ua "
			+ "on o.uaid=ua.id where o.id=#{oid}")
	@ResultType(Order.class)
	public ViewOrder queryOrder(String oid);
	 
	//查询订单子项（就是查某个指定订单里的商品详情，包括商品价格，数量，总的钱数，这里也需要两张表）
	@Select("select g.*,od.num,od.money from t_orderdetail od left join t_goods g "
			+ "on od.gid=g.id where od.oid=#{oid}")
	@ResultType(ViewOrderDetail.class)
	public List<ViewOrderDetail> queryDetailList(String oid);
	
	//查询订单的详情信息
	@Select(" select g.*,od.num,od.money,o.createtime,o.money,"
			+ "ua.name,ua.phone,ua.detail from t_orderdetail od "
			+ "left join t_goods g on od.gid=g.id left join t_order o on od.oid=o.id"
			+ " left join t_useraddress ua on o.uaid=ua.id where "
			+ "od.oid=#{oid}")
	@ResultType(Order.class)
	public ViewOrder queryDetail(String oid);
	
	//查询全部的订单
	@Select("select o.*,u.username from t_order o left join t_user u "
			+ "on o.uid=u.id")
	@ResultType(Order.class)
	public List<Order> queryAll();
	
	//根据用户的姓名和订单的支付状态查询订单（admin）
	@Select("<script>"+
			"select o.*,u.username from t_order o left join t_user u on o.uid=u.id "+
			"<where>"+
			"<if test='username != null'>"+
			" and u.username like concat('%',#{username},'%') "+
			"</if>"+
			"<if test='flag != null'>"+
			"and o.flag=#{flag}"+
			"</if>"+
			"</where>"+
			"</script>")
	@ResultType(Order.class)
	public List<Order> selectByNameAndFlag(@Param("username")String username,
			@Param("flag")Integer flag);//因为要对flag进行null判断，所以需要用到包装类
	
	//删除订单
	@Delete("delete from t_order where id=#{id}")
	public int deleteById(@Param("id")String id);
	
}
