package com.qfedu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.domain.Cart;
import com.qfedu.domain.Goods;
import com.qfedu.domain.User;
import com.qfedu.service.CartService;

@Controller
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	//添加商品到购物车里
	@RequestMapping("addCart")
	public String add(int gid, double price, HttpSession session){
		//使用Sprig框架后是不是什么都是不需要new的？还是说都是注入？可以new吗？
		Goods goods = new Goods();
		goods.setId(gid);
		goods.setPrice(price);
		//判断用户是否为登录状态，只有登录了才有session，才能查到购物车，否则无法添加商品
		if(session.getAttribute("cart") == null) return "login";
		if(cartService.add(((Cart)session.getAttribute("cart")).getId(), goods, 1)){
			//成功并跳转到购物车页面
			return "cartSuccess";
		}else{
			return "index";
		}
	}
	
	//查询购物车
	@RequestMapping("getCart")
	public String get(HttpServletRequest request){
		//在Controller里面没有自带session域，所以可以通过request来获取
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) return "login";
		request.setAttribute("userCart", cartService.queryCart(cart.getId()));
		//注意：这里是转发，也就是说地址栏不会改变，而重定向（redirect：）的地址栏会发生变化
		return "cart";
	}
	
	//删除购物车里的商品
	@RequestMapping("clearCart")
	public String clearCart(HttpServletRequest request, int gid){
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart == null) return "index";
		cartService.deleteDetail(cart.getId(), gid);
		return "redirect:getCart";
	}
	
	//通过按钮添加或者减少商品的数量
	@RequestMapping("updateCartNum")
	@ResponseBody
	public int update(int gid, double price, int num, HttpServletRequest request){
		Goods gd = new Goods();
		gd.setId(gid);
		gd.setPrice(price);
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//如果用户没有登录就无法操作
		if(cart == null) return 0;
		cartService.changeNum(cart.getId(), gd, num);
		//如果操作成功，就会返回1，表示成功，反正失败返回0
		return 1;
		
	}
	
	
	
	
	
	
	
	
	
}
