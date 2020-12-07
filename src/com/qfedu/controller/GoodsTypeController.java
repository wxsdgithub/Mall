package com.qfedu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.domain.GoodsType;
import com.qfedu.service.GoodsTypeService;

@Controller
public class GoodsTypeController {
	@Autowired
	private GoodsTypeService service;
	
	//跳转到新增商品类型新增页面
	@RequestMapping("goodstypeshowadd")
	public String showadd(HttpServletRequest request,Model model){
		//之所以要用model存起来，就是为了给对应的jsp页面填充数据
		model.addAttribute("gtlist", service.queryByLevel());
		return "addGoodsType";
	}
	
	//实现新增商品类型功能
	@RequestMapping("goodstypeadd")
	public String add(GoodsType goodsType,HttpServletRequest request,Model model){
		if("1".equals(goodsType.getParentName())){
			goodsType.setLevel(1);
			goodsType.setParentName(null);
		}else{
			goodsType.setLevel(2);
		}
		//设置该商品类型有效
		goodsType.setFlag(1);
		if(service.save(goodsType)){
			return "redirect:getGoodsType";
		}else{
			model.addAttribute("msg", "服务器异常，请稍后再来");
			//失败的话，就跳转到商品类型添加页面
			return "redirect:goodstypeshowadd";
		}
	}
	
	//实现商品类型删除
	@RequestMapping("deleteGoodsType")
	@ResponseBody
	public String deleteGoodsType(HttpServletRequest request, int count){
		service.deleteType(count);
		return "success";
	}
	
	//显示商品类型列表
	@RequestMapping("getGoodsType")
	public String show(HttpServletRequest request,Model model){
		model.addAttribute("gtlist", service.queryAll());
		return "/admin/showGoodsType";
	}
	
	
	//index页面上方的类型排列，在显示index页面的时候会自动调用该方法
	@RequestMapping("goodstypejson")
	@ResponseBody
	public List<GoodsType> showjson(){
		return service.queryByLevel();
	}
	
	//根据商品等级和商品名称查询类型（admin）
	@RequestMapping("selectByNameAndFlag")
	public String queryNameAndFlag(String name, int flag, Model model){
		model.addAttribute("gtlist", service.queryNameAndFlag(name, flag));
		//System.out.println(name+","+flag+service.queryNameAndFlag(name, flag));
		return "/admin/showGoodsType";
	}
	
	
	
	
	
	
	
	
}
