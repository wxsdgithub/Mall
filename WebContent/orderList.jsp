<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>锋迷网-订单列表</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	function showOrder(orderId){
		location.href="${pageContext.request.contextPath}/getOrderDetail?oid="+orderId;
	}
	function deleteOrder(orderId){
		location.href="${pageContext.request.contextPath}/userDeleteOrder?oid="+orderId;
	}
	function changeStatus(orderId){
		location.href="${pageContext.request.contextPath}/changeStatus?oid="+orderId;
	}
</script>
</head>
<body style="background-color:#f5f5f5">
<%@ include file="header.jsp"%>
<div class="container" style="background-color: white;">
	<div class="row" style="margin-left: 40px">
		<h3>我的订单列表&nbsp;&nbsp;
		<small>温馨提示：<em>${loginUser.username}</em>有<font color="red">${orderList.size()}</font>个订单</small></h3>
	</div>
	<div class="row" style="margin-top: 40px;">
		<div class="col-md-12">
			<table id="tb_list" class="table table-striped table-hover table-bordered table-condensed">
			<tr>
				<th>序号</th>
				<th>订单编号</th>
				<th>总金额</th>
				<th>订单状态</th>
				<th>订单时间</th>
				<th>操作</th>
				<th>谨慎处理</th>
			</tr>
			<c:forEach items="${orderList}" var="order" varStatus="i">
				<tr>
					<th>${i.count}</th>
					<th>${order.id}</th>
					<th>${order.money}</th>
					<th>
						<font color="red">
							<c:if test="${order.flag eq 1 }">
								未支付
							</c:if>
							<c:if test="${order.flag eq 2 }">
								已支付,待发货
							</c:if>
							<c:if test="${order.flag eq 3 }">
								已发货,待收货
							</c:if>
							<c:if test="${order.flag eq 4 }">
								已收货，未评分
							</c:if>
							<c:if test="${order.flag eq 5 }">
								订单完成
							</c:if>
						</font>
					</th>
					<th>${order.createtime}</th>
					<th>
						<button type="button" class="btn btn-danger btn-sm" onclick="showOrder('${order.id}')">订单详情</button>
						<%-- <c:if test="${order.flag eq 3 }">
							<button type="button" class="btn btn-warning btn-sm" onclick="changeStatus('${order.id}')">确认收货</button>
						</c:if> --%>
					</th>
					<th>
						<button type="button" class="btn btn-danger btn-sm" onclick="deleteOrder('${order.id}')">删除订单</button>
					</th>
				</tr>
			</c:forEach>
		</table>
		</div>
	</div>
</div>
<!-- 底部 -->
<%@ include file="footer.jsp"%>
</body>
</html>