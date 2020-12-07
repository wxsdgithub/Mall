package com.qfedu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qfedu.common.utils.RandomUtils;
import com.qfedu.common.vo.ViewCart;
import com.qfedu.domain.Cart;
import com.qfedu.domain.CartDetail;
import com.qfedu.domain.Goods;
import com.qfedu.domain.OrderDetail;
import com.qfedu.domain.User;
import com.qfedu.service.CartService;
import com.qfedu.service.OrderService;
import com.qfedu.service.UserAddressService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserAddressService userAddressService;
	@Autowired
	private CartService cartService;
	
	//下单(就是订单页面的“提交订单”，有两种情况，一种是直接下单转过来的，所以t(type)为1
	//第二种情况是通过购物车转过来的，那就是type=2
	@RequestMapping("addOrder")
	public String add(int t, int aid, HttpSession session, Model model, HttpServletRequest request){
		User user = (User)session.getAttribute("user");
		if(user == null) return "login";
		Cart cart = (Cart)session.getAttribute("cart");
		if(cart == null) return "login";
		//随机产生一个订单号
		String oid = RandomUtils.createOrderId();
		boolean res = false;
		CartDetail di= null;
		double sum=0;
		if(t == 1){
			//类型 t ==1 时表示直接下单，即调用直接下单的方法
			//下边的direct值是在商品详情页面那里点击直接下单所保存的
			CartDetail direct= (CartDetail) session.getAttribute("direct");
			if(direct == null) return "index";
			//将当前商品的信息赋给局部变量，便于下面的存储
			di = direct;
			res = orderService.insertDirect(user.getId(), oid, aid,direct);
		}else{
			//如果不是直接下单，在本系统里是默认把购物车的东西全部下单
			//后期可以修改下，让选择了商品才能下单，现在先不管
			
			List<ViewCart> cds = cartService.queryCart(cart.getId());
			
			if(cds.size() == 0) return "login";
			//创建一个订单详情（子项），包含很多的商品信息
			for(int i=0; i < cds.size(); i++){
				sum+=cds.get(i).getMoney();
			}
			
			res = orderService.save(oid, user.getId(), aid);
			
		}
		
		if(res && t == 1){
			//此时是直接下单成功，
			request.setAttribute("oid", oid);
			request.setAttribute("omoney", di.getMoney());
			return "payDirectAndCart";
		}else if(res && t == 2){
			request.setAttribute("oid", oid);
			request.setAttribute("omoney", sum);
			return "payDirectAndCart";
		}else{
			return "index";
		}
	}
	
	//直接下单（在商品详情那里下单）
	@RequestMapping("getDirectOrder")
	public String direct(Goods gs, Model model, HttpSession session){
		//是不是每次只能下单一件商品？
		
		User user = (User)session.getAttribute("user");
		if(user == null) return "login";
		//这些是为了给订单页面（order.jsp)填充数据用的，考虑到可能不止一件商品，所以用数组
		List<ViewCart> cds = new ArrayList<ViewCart>();
		ViewCart cd = new ViewCart();
		cd.setGid(gs.getId());
		cd.setMoney(gs.getPrice());
		cd.setPrice(gs.getPrice());
		cd.setNum(1);
		cd.setName(gs.getName());
		cds.add(cd);
		
		//这里之所以还要弄个CartDetail是为了给上边的下单方法（addOrder)传CartDetail参数
		CartDetail detail = new CartDetail();
		detail.setGid(gs.getId());
		detail.setMoney(gs.getPrice());
		detail.setNum(1);
		session.setAttribute("direct", detail);
		//这里存储的是用户所想下单的所有商品
		model.addAttribute("cartList", cds);
		//这里存储的是当前用户所有的地址，但是可能没有，也可能不符合，可能用户想要换地址
		//不过可以先展示出来，然后让用户自己选择
		model.addAttribute("addList", userAddressService.queryByUid(user.getId()));
		//这里存储当前下单的类型，如果是type=1就是直接下单
		model.addAttribute("type", 1);
		return "order";
		
	}
	
	//查询用户所有的订单(登录成功后，index.jsp页面上的点击“我的订单”)
	@RequestMapping("getOrderList")
	public String olist(HttpSession session, Model model){
		User user = (User)session.getAttribute("user");
		if(user == null) return "login";
		
		model.addAttribute("orderList", orderService.queryByUid(user.getId()));
		
		return "orderList";
	}
	
	//订单详情列表查询(在订单列表页面上点击“订单详情”）
	@RequestMapping("getOrderDetail")
	public String list(String oid, HttpSession session, Model model){
		model.addAttribute("od", orderService.queryOrderdetailById(oid));
		return "orderDetail";
	}
	
	//管理员查询所有用户的订单
	@RequestMapping("getAllOrder")
	public String all(Integer t, Integer aid, Model model){
		model.addAttribute("orderList", orderService.queryAll());
		return "/admin/showAllOrder";
	}
	
	//订单预览，即点击购物车页面的“结算”
	@RequestMapping("getOrderView")
	public String viewlist(HttpServletRequest request, Model model){
	//事实上model数据，最终spring也是写到HttpServletRequest属性中，只是用model更符合mvc设计,减少各层间耦合。
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) return "login";
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		if(cart == null) return "login";
		model.addAttribute("type", 2);
		//找出该用户所有的商品
		request.setAttribute("cartList", cartService.queryCart(cart.getId()));
		request.setAttribute("addList", userAddressService.queryByUid(user.getId()));
		
		return "order";
	}
	
	//管理员根据用户姓名和订单的支付状态查询订单（admin)
	@RequestMapping("selectOrderByNameAndFlag")
	public String selectOrderByNameAndFlag(String username, Integer status, Model model){
		model.addAttribute("orderList", orderService.selectByNameAndFlag(username, status));
		return "/admin/showAllOrder";
	}
	
	//用户删除订单,在订单页面，点击“删除订单”
	@RequestMapping("userDeleteOrder")
	public String usrDeleteOrder(String oid, HttpSession session, Model model){
		orderService.deleteById(oid);
		//切记：当需要跳转页面时，如果是已经有现成jsp，那就不需要转发（redirect：）
		//如果需要通过url进入Controller的方法，那就需要转发的。
		return "redirect:getOrderList";
	}
	
	//管理员修改订单状态，特指管理员发货,flag=3表示发货
	@RequestMapping("sendOrder")
	public String sendOrder(String oid, Model model){
		orderService.update(oid, 3);
		return "redirect:getAllOrder";
	}
	
}
