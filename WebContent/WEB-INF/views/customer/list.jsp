<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ taglib prefix="atguigu" tagdir="/WEB-INF/tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>客户基本信息管理</title>
</head>
<body>

	<div class="page_title">客户基本信息管理</div>
	<div class="button_bar">
		<button class="common_button" onclick="document.forms[0].submit();">查询</button>
	</div>
	
	<form action="${ctp}customer/list" method="POST">
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>客户名称</th>
				<td>
					<input type="text" name="search_LIKES_name"/>
				</td>
				<th>地区</th>
				<td>
					<select name="search_EQS_region">
						<option value="">全部</option>
						<c:forEach items="${regions }" var="region">
							<option value="${region }">${region }</option>
						</c:forEach>
					</select>
				</td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>客户经理</th>
				<td><input type="text" name="search_LIKES_manager.name" /></td>
				
				<th>客户等级</th>
				<td>
					<select name="search_EQS_level">
						<option value="">全部</option>
						<c:forEach items="${levels }" var="level">
							<option value="${level }">${level }</option>
						</c:forEach>
					</select>
				</td>
				
				<th>状态</th>
				<td>
					<select name="search_EQS_state">
						<option value="">全部</option>
						<option value="正常">正常</option>
						<option value="流失">流失</option>
						<option value="删除">删除</option>					
					</select>
				</td>
			</tr>
		</table>
		
		<!-- 列表数据 -->
		<br />
		<c:if test="${page.numberOfElements != 0 }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>客户编号</th>
					<th>客户名称</th>
					<th>地区</th>
					<th>客户经理</th>
					<th>客户等级</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${page.content }" var="item">
					<tr>
						<td class="list_data_text">${item.no }&nbsp;</td>
						<td class="list_data_ltext">${item.name }&nbsp;</td>
						<td class="list_data_text">${item.region }&nbsp;</td>
						<td class="list_data_text">${item.manager.name }&nbsp;</td>
						<td class="list_data_text">${item.level }&nbsp;</td>
						<td class="list_data_text">${item.state }&nbsp;</td>
						<td class="list_data_op">
							<img onclick="window.location.href='${ctp}customer/create?id=7046'"
								title="编辑" src="${ctp}static/images/bt_edit.gif" class="op_button" alt="" /> 
							<img onclick="window.location.href='${ctp}contact/list?customerid=7046'"
								title="联系人" src="${ctp}static/images/bt_linkman.gif" class="op_button" alt="联系人信息" /> 
							<img onclick="window.location.href='${ctp}activity/list?customerid=7046'"
								title="交往记录" src="${ctp}static/images/bt_acti.gif" class="op_button" alt="交往记录" /> 
							<img onclick="window.location.href='${ctp}order/list?customerid=7046'"
								title="历史订单" src="${ctp}static/images/bt_orders.gif" class="op_button" alt="历史订单" /> 
								
									<img onclick="window.location.href='${ctp}customer/delete?id=7046'" 
									title="删除" src="${ctp}static/images/bt_del.gif" class="op_button" alt="删除" />
								
							</td>					
					</tr>
				</c:forEach>
			</table>
		</c:if>	
		<atguigu:pagination paginationSize="5" page="${page }"></atguigu:pagination>		
	</form>
	
</body>
</html>