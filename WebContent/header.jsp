<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link href="css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<title></title>
<script type="text/javascript">
	$(function(){
		//页面加载 就要完成商品类型的加载
		 $.ajax({
			url:"${pageContext.request.contextPath}/goodstypejson",
			method:"get",
			success:function(list){
				//var list = JSON.parse(data);
				for(var t in list){
					var a = $("<a href='${pageContext.request.contextPath}/getGoodsListByTn?tn="+list[t].name+"'>"+list[t].name+"</a>");
					//下面就相当与创建一个goodsType变量,本质上是去取出第69行的id，并赋值
					$("#goodsType").append(a);
				}
			},
			error:function(){
				alert("失败");
			}
		}) 
	})
</script>
</head>
<body>			
 <div id="top">
    	<div id="topdiv">
            <span>
                <a href="index.jsp" id="a_top" target="_blank">锋迷网</a>
                <li>|</li>
                <a href="" id="a_top">锋迷网网页版</a>
                <li>|</li>
                <a href="" id="a_top">问题反馈</a>
            </span>
            <span style="float:right">
           		<c:if test="${empty user}">
	                <a href="login.jsp" id="a_top">登录</a>
	                <li>|</li>
	                <a href="register.jsp" id="a_top">注册</a>
           		</c:if>
       			<c:if test="${not empty user}">
       				<a href="" id="a_top">${user.username}</a>
       				<li>|</li>
       				<a href="userloginout" id="a_top">注销</a>
       				<li>|</li>
       				<a href="getOrderList" id="a_top">我的订单</a>
       				<li>|</li>
       				<a href="userAddressShow" id="a_top">地址管理</a>
       			</c:if>
                <li>|</li>
                <a href="" id="a_top">消息通知</a>
                <a href="${pageContext.request.contextPath}/getCart" id="shorpcar">购物车</a>
            </span>
        </div>
 </div>
<div>
<table style="height:100px;width:86%" align="center">
	<tr>
	<td>
		<img id="logo" src="image/logo.png" width="200px" height="60px"/>
	</td>
	<td>
		<p id="goodsType" style="padding:10px;margin-left: 20px"></p>
	</td>
	<td align="right">
		<form action="selectByName" class="form-inline pull-right">
			  <div class="form-group" >
			    	<input type="text" name="name" class="form-control" style="width: 300px"  placeholder="幸福生活，搜索一下...">
			  </div>
			  <button type="submit" class="btn btn-warning"><span class="glyphicon glyphicon-search"></span>&nbsp;&nbsp;搜索</button>
		 </form>
	</td>
	</tr>
</table>
</div>
</body>
</html>