package com.qfedu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qfedu.domain.User;
import com.qfedu.domain.UserAddress;
import com.qfedu.service.UserAddressService;

@Controller
public class UserAddressController {
	@Autowired
	private UserAddressService userAddressService;
	
	//查找用户地址信息
		@RequestMapping("userAddressShow")
		public String show(UserAddress ua, HttpServletRequest request){
			User user = (User)request.getSession().getAttribute("user");
			if(user == null) return "login";
			request.setAttribute("addList",userAddressService.queryByUid(user.getId()));
			return "self_info";
		}
	
	//删除地址信息
	@RequestMapping("userAddressDel")
	public String userAddressDel(int id, HttpServletRequest request){
		userAddressService.deleteAddress(id);
		return "redirect:userAddressShow";
	}
	
	//添加用户地址信息
	@RequestMapping("userAddressAdd")
	public String add(UserAddress ua, HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) return "login";
		ua.setUid(user.getId());
		
		if(userAddressService.insert(ua)){
			request.setAttribute("addList", userAddressService.queryByUid(user.getId()));
		}
		
		return "self_info";
	}
	
	//修改地址
	@RequestMapping("useraddressupdate")
	public String update(UserAddress ua, Model model, HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) return "login";
		if(userAddressService.update(ua)){
			model.addAttribute("addList", userAddressService.queryByUid(user.getId()));
		}
		return "self_info";
	}
	
	//修改默认地址
	@RequestMapping("userAddressDef")
	public String userAddressDef(Integer id, HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) return "login";
		userAddressService.updteDea(id, user.getId());
		return "redirect:userAddressShow";
	}
	
	
	
}
