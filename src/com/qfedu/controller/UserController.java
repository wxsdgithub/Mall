package com.qfedu.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.common.utils.EmailUtils;
import com.qfedu.common.utils.MD5Utils;
import com.qfedu.common.utils.RandomUtils;
import com.qfedu.common.vo.ResultBean;
import com.qfedu.domain.Cart;
import com.qfedu.domain.User;
import com.qfedu.service.CartService;
import com.qfedu.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
	
	//登录
	@RequestMapping(value="/userlogin")
	public String login(String username,String password,Model model,
			HttpSession session,HttpServletRequest request){
		if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
			User user = userService.getUserByName(username);
			if(user != null){
				//验证密码
				if(user.getPassword().equals(MD5Utils.md5(password))){
					//记录信息到信息会话里，这里主要是给返回的页面，而下面的session是用户的整个活动过程
					//model.addAttribute("user", user);
					session.setAttribute("user", user);
					//查看是数据库的监视器否成功创建用户的专属购物车,如果没有就自己手动添加
					Cart cart = cartService.queryByUid(user.getId());
					if(cart == null){
						cart = new Cart();
						cart.setUid(user.getId());
						cart.setMoney(0);
						cartService.createCart(cart);
					}
					//用户一旦登录成功就一定要把购物车（cart）放到session（会话）里
					//目的是为了用户添加商品到购物车里做检查
					session.setAttribute("cart", cartService.queryByUid(user.getId()));
					return "index";
				}
			}
		}
		model.addAttribute("loginMsg","用户名或者密码错误");
		//request.setAttribute("loginMsg", "用户名或者密码错误");
		return "login";
	}
	
	//注册
	@RequestMapping(value="/userregister")
	public String register(User user,Model model,HttpSession session){
		//创建激活码
		String acode = RandomUtils.createActive();
		user.setActivatecode(acode);
		
		//用户添加成功
		if(userService.save(user)){
			
			session.setAttribute("acode", acode);
			//发送激活码
			EmailUtils.sendEmail(user);
			return "registerSuccess";
		}
		//注册失败
		else{
			model.addAttribute("registerMsg", "服务器开小差，请稍候再来");
			return "register";
		}
	}
	
	//注销
	@RequestMapping(value="/userloginout")
	public String loginout(String type,HttpSession session){
		if(type != null){
			session.removeAttribute("adminuser");
			return "admin/login";
		}
		else{
			session.removeAttribute("user");
			return "index";
		}
	}
	
	//检验用户名是否可用
	@RequestMapping(value="/usercheckname")
	//检验用户名是否可用其实是局部更新，经常需要用到json字串，所以需要此注解
	//即：一般来说，不返回页面的往往需要该注解，因为这样就返回json或者xml字符串
	@ResponseBody
	public ResultBean checkname(String name){
		//创建激活码
		if(userService.checkName(name)){
			return ResultBean.setSuccess("OK");
		}else{
			return ResultBean.setError("ERROR");
		}
	}
	
	//删除用户
	@RequestMapping(value="/userdel")
	@ResponseBody
	public int checkname(int id){
		return userService.deleteByid(id);
	}
	
	//用户搜索
	@RequestMapping(value="/userserach")
	@ResponseBody
	public List<User> usersearch(String username,String gender){
		List<User> users = userService.usersearch(username, gender);
		return users;
	}
	
	//检验邮箱是否可用
	@RequestMapping(value="/usercheckemail)")
	@ResponseBody
	public ResultBean checkemail(String email){
		//创建激活码
		if(userService.checkEmail(email)){
			return ResultBean.setSuccess("OK");
		}else{
			return ResultBean.setError("ERROR");
		}
	}
	
	//用户列表
	@RequestMapping(value="/userlist")
	@ResponseBody
	public List<User> list(){
		return userService.selectAll();
	}
	
	//管理员登录
	@RequestMapping(value="/adminLogin")
	public String adminLogin(String username,String password,HttpServletRequest request){
		if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
			User user = userService.getUserByName(username);
			if(user != null){
				//检验密码
				if(user.getPassword().equals(MD5Utils.md5(password))){
					request.setAttribute("adminuser", user);
					return "admin/admin";
				}
			}
		}
		request.setAttribute("msg", "用户名或者密码有误");
		return "admin/login";
	}
	
	//激活激活码
	@RequestMapping(value="/activate")
	public String checkCode(String e, String c, HttpSession session){
		if(userService.activateUser(e,e)){
			return "login";
		}else{
			userService.activateUser(e, c);
			return "index";
		}
	}
	
	
	
}	
