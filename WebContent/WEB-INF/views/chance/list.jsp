<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售机会管理</title>
<script type="text/javascript">
	$(function(){
		$("#new").click(function(){
			window.location.href="${ctp}chance/chance";
			return false;
		});
		
		//删除: 正常情况下, 删除都是一个超级链接, 需要通过 js 把其转为一个 POST 请求. 额外借助一个 form
		$("img[id^='delete-']").click(function(){
			var id = $(this).next(":hidden").val();
			var _form = $("#_hiddenForm");
			_form.attr("action", _form.attr("action") + id);
			_form.submit();
			return false;
		});
	})
</script>
</head>
<body class="main">
	<form action="${ctp }chance/chance/" method="POST" id="_hiddenForm">
		<input type="hidden" name="_method" value="DELETE"/>
	</form>

	<form id="command" action="${ctp}chance/list" method="post">
		<div class="page_title">
			销售机会管理
		</div>
		<div class="button_bar">
			<button class="common_button" id="new">
				新建
			</button>
			<button class="common_button" onclick="document.forms[1].submit();">
				查询
			</button>
		</div>
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title">
					客户名称
				</th>
				<td class="input_content">
					<input type="text" name="search_LIKES_custName" />
				</td>
				<th class="input_title">
					概要
				</th>
				<td class="input_content">
					<input type="text" name="search_LIKES_title" />
				</td>
				<th class="input_title">
					联系人
				</th>
				<td class="input_content">
					<input type="text" name="search_LIKES_contact" />
				</td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
		
		<c:if test="${page == null || !(page.numberOfElements > 0) }">
			当前页面没有能显示的记录
		</c:if>
		
		<c:if test="${page != null && page.numberOfElements > 0 }">
		
			<table class="data_list_table" border="0" cellPadding="3" cellSpacing="0">
				<tr>
					<th>
						编号
					</th>
					<th>
						客户名称
					</th>
					<th>
						概要
					</th>
					<th>
						联系人
					</th>
					<th>
						联系人电话
					</th>
					<th>
						创建时间
					</th>
					<th>
						操作
					</th>
				</tr>
				
				<c:forEach items="${page.content }" var="item">
					<tr>
						<td class="list_data_number">${item.id }</td>
						<td class="list_data_text">${item.custName }</td>
						<td class="list_data_text">${item.title }</td>
						<td class="list_data_text">${item.contact }</td>
						<td class="list_data_text">${item.contactTel }</td>
						<td class="list_data_text">
							<fmt:formatDate value="${item.createDate }" pattern="yyyy-MM-dd"/>
						</td>
						<td class="list_data_op">
							<img onclick="window.location.href='${ctp}chance/dispatch?id=6140'" 
								title="指派" src="${ctp}static/images/bt_linkman.gif" class="op_button" />
							<img onclick="window.location.href='${ctp}chance/chance/${item.id }'" 
								title="编辑" src="${ctp}static/images/bt_edit.gif"
								class="op_button" />
							<img id="delete-${item.id }"
								title="删除" src="${ctp}static/images/bt_del.gif" class="op_button" />
							<input type="hidden" value="${item.id }"/>	
						</td>
					</tr>
				</c:forEach>
			</table>

			<div style="text-align:right; padding:6px 6px 0 0;">
				共 ${page.totalElements } 条记录 
				&nbsp;&nbsp;
				
				当前第 ${page.number }  页/共 ${page.totalPages }  页
				&nbsp;&nbsp;
				
				<c:if test="${!page.firstPage }">
					<a href='?page=1&${queryString }'>首页</a>
					&nbsp;&nbsp;
					<a href='?page=${page.number - 1 }&${queryString }'>上一页</a>
					&nbsp;&nbsp;
				</c:if>
				
				<c:if test="${!page.lastPage }">
					<a href='?page=${page.number + 1 }&${queryString }'>下一页</a>
					&nbsp;&nbsp;
					<a href='?page=${page.totalPages }&${queryString }'>末页</a>
					&nbsp;&nbsp;
				</c:if>
	
				转到 <input id="pageNo" size='1'/> 页
				&nbsp;&nbsp;
			</div>

		<script type="text/javascript">
			$(function(){
				
				$("#pageNo").change(function(){
					
					var pageNo = $(this).val();
					var reg = /^\d+$/;
					if(!reg.test(pageNo)){
						$(this).val("");
						alert("输入的页码不合法");
						return;
					}
					
					var pageNo2 = parseInt(pageNo);
					if(pageNo2 < 1 || pageNo2 > parseInt("${page.totalPages }")){
						$(this).val("");
						alert("输入的页码不合法");
						return;
					}
					
					//查询条件需要放入到 class='condition' 的隐藏域中. 
					window.location.href = window.location.pathname
						+ "?page=" + pageNo2 + "&${queryString }";
					
				});
			})
		</script>
		
	</c:if>	
		
	</form>
	
</body>
</html>