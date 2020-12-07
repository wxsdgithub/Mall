<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link href="css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<title>锋迷网</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div id="thred" style="height:310px">
		<div id="myCarousel" class="carousel slide" >
			<!-- 轮播（Carousel）指标 -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>
			<!-- 轮播（Carousel）项目 -->
			<div class="carousel-inner">
				<div class="item active">
					<img src="image/b1.JPG" alt="First slide">
				</div>
				<div class="item">
					<img src="image/b2.JPG" alt="Second slide">
				</div>
				<div class="item">
					<img src="image/b3.JPG" alt="Third slide">
				</div>
			</div>
			<!-- 轮播（Carousel）导航 -->
			<a class="left carousel-control" href="#myCarousel" role="button"
				data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				<span class="sr-only">上一张</span>
			</a> <a class="right carousel-control" href="#myCarousel" role="button"
				data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">下一张</span>
			</a>
		</div>
	</div>
	<div class="fifth">
		<span class="fif_text">热销饮料</span>
	</div>
	<div class="sixth" id="data1">
		
	</div>
	<div class="fifth">
		<span class="fif_text">热销酒类</span>
	</div>
	<div class="sixth" id="data2">
		
	</div>
	<div class="fifth">
		<span class="fif_text">热销零食</span>
	</div>
	<div class="sixth" id="data3">
		
	</div>
	<script type="text/javascript">
	//这里其实就是jQuery的入口函数，入口函数就是预先加载完所有的标签，然后执行入口函数内的jQuery代码
	$(function() {
		$.get("getGoodsIndex",null,function(arr){
			var s="";
			for(var i=0;i<arr[0].length;i++){
				var g=arr[0][i];
				s+="<span class='sindex'><a class='siximg' href='${pageContext.request.contextPath}/getGoodsById?id="+
						g.id+"'><img src='./fmwimages/"+g.picture+"' width='234px' height='234px' /></a><a class='na'>"+
						g.name+"</a><p class='chip'>"+g.intro+"</p><p class='pri'>￥"+g.price+"元</p></span>";
			}
			$("#data1").html(s);
			s="";
			for(var i=0;i<arr[1].length;i++){
				var g=arr[1][i];
				s+="<span class='sindex'><a class='siximg' href='${pageContext.request.contextPath}/getGoodsById?id="+
						g.id+"'><img src='./fmwimages/"+g.picture+"' width='234px' height='234px' /></a><a class='na'>"+
						g.name+"</a><p class='chip'>"+g.intro+"</p><p class='pri'>￥"+g.price+"元</p></span>";
			}
			$("#data2").html(s);
			s="";
			for(var i=0;i<arr[2].length;i++){
				var g=arr[2][i];
				s+="<span class='sindex'><a class='siximg' href='${pageContext.request.contextPath}/getGoodsById?id="+
						g.id+"'><img src='./fmwimages/"+g.picture+"' width='234px' height='234px' /></a><a class='na'>"+
						g.name+"</a><p class='chip'>"+g.intro+"</p><p class='pri'>￥"+g.price+"元</p></span>";
			}
			$("#data3").html(s);
			
		}); 
	});
	</script>
	<!-- 底部 -->
	<%@ include file="footer.jsp"%>
</body>
</html>