package com.qfedu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qfedu.common.utils.FileUtils;
import com.qfedu.domain.Goods;
import com.qfedu.service.GoodsService;
import com.qfedu.service.GoodsTypeService;

@Controller
public class GoodsController {
	
	@Autowired
	private GoodsTypeService goodTypeService;
	@Autowired
	private GoodsService goodsService;
	
	//跳转到商品类型新增页面
	@RequestMapping("toAddGoods")
	public String showadd(HttpServletRequest request,Model model){
		model.addAttribute("gtlist", goodTypeService.queryByLevel());
		return "addGoods";
	}
	
	// 商品新增
		@RequestMapping("addGoods")
		public String save(MultipartFile file, Goods goods, HttpServletRequest request)
				throws IllegalStateException, IOException {
			File dir = FileUtils.createDir("C:\\Users\\18741\\workspace\\ElectronicMall\\WebContent\\fmwimages");
            //File dir = FileUtils.createDir(request.getServletContext().getRealPath("/"));
			File desFile = new File(dir, FileUtils.createFileName(file.getOriginalFilename()));
			file.transferTo(desFile);
			goods.setPicture(dir.getName() + "/" + desFile.getName());
			goods.setFlag(1);//上架
			if (goodsService.save(goods)) {
				return "addGoods";
			} else {
				request.setAttribute("msg", "添加失败，重新再来");
				return "addGoods";
			}
		}
	
	//查看商品详情,查看某一件商品的信息
	@RequestMapping("getGoodsById")
	public String goodsbyid(int id,Model model){
		model.addAttribute("goods", goodsService.querySingle(id));
		return "goodsDetail";
	}
	
	//该事件是在用户进入index界面时，index的函数自己调用该方法，使得一些商品在index展示出来
	@RequestMapping("getGoodsIndex")
	@ResponseBody
	public List<List<Goods>> goodsindex(HttpServletResponse response){
		return goodsService.queryIndex();
	}
	
	//查看用类型查出来的商品列表
	@RequestMapping("getGoodsListByTn")
	public String goodsbytn(String tn, Model model){
		model.addAttribute("glist", goodsService.queryByType(tn));
		return "goodsList";
	}
	
	//查看所有的商品（商品列表）
	@RequestMapping("getGoodsList")
	public String goodslist(Model model){
		model.addAttribute("goodsList", goodsService.queryAll());
		return "/admin/showGoods";
	}
	
	//模糊查询商品列表(用户在index界面查询)
	@RequestMapping("selectByName")
	public String selectByName(String name,Model model){
		model.addAttribute("glist", goodsService.queryByName(name));
		return "goodsList";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
