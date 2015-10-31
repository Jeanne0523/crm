<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>新建销售机会</title>
    <script type="text/javascript">
	    $(function(){
	    	var val = $("#createDate").val();

	    	if(val == null || val == ""){
	    		$("#createDate").val(new Date().format("yyyy-MM-dd"));
	    	}
	    	
	    	$("#save").click(function(){
    			$("form")[0].submit();
	    	});
	    })
    </script>
    
 </head>
 <body class="main">
 	<span class="page_title">新建/修改销售机会</span>
	<div class="button_bar">
		<button class="common_button" onclick="javascript:history.go(-1);">返回</button>
		<button class="common_button" id="save">保存</button>
	</div>
	
	<c:if test="${chance.id == null}">
		<c:url value="/chance/chance" var="url"></c:url>
	</c:if>
	<c:if test="${chance.id != null }">
		<c:url value="/chance/chance/${chance.id }" var="url"></c:url>
	</c:if>
	
	<form:form action="${url }" method="POST" modelAttribute="chance">
		<c:if test="${chance.id != null }">
			<!--  
			对于 id, 既可以使用 form:hidden, 也可以使用 input hidden.
			使用 form:hidden 可以自动回显. 而使用 input hidden, 还需要编写 value 属性值. 
			-->
			<form:hidden path="id"/>
			<!--  
			对于 _method 必须使用 input 标签的 hidden, 因为若使用了 form:hidden, 则
			SpringMVC 就会去 bean 中查找 _method 的属性, 而 bean 中根本就没有, 然后就会报错. 
			-->
			<input type="hidden" name="_method" value="PUT"/>
		</c:if>	
	
		<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>编号</th>
				<td>
					<form:input path="id" readonly="true"/>
					&nbsp;
				</td>
				<th>机会来源</th>
				<td><form:input path="source"/></td>
			</tr>
			<tr>
				<th>客户名称</th>
				<td><form:input path="custName"/><span class="red_star">*</span></td>
				<th>成功机率（%）<br />填入数字，0~100</th>
				<td><form:input path="rate"/><span class="red_star">*</span></td>
			</tr>
			<tr>
				<th>概要</th>
				<td colspan="2"><form:input path="title"/><span class="red_star">*</span></td>
			</tr>
			<tr>
			<th>联系人</th>
			<td><form:input path="contact"/></td>
			<th>联系人电话</th>
			<td><form:input path="contactTel"/></td>
			</tr>
			<tr>
				<th>机会描述</th>
				<td colspan="3">
					<form:textarea path="description" rows="6" cols="50"/>
					<span class="red_star">*</span>
				</td>
			</tr>
			<tr>
				<th>创建人</th>
				<td>
					<c:if test="${chance.id == null}">
						${sessionScope.user.name }
					</c:if>
					<c:if test="${chance.id != null }">
						${chance.createBy.name }
					</c:if>
					<form:hidden path="createBy.id"/>
				</td>
				
				<th>创建时间</th>
				<td><form:input id="createDate" path="createDate" readonly="true"/><span class="red_star">*</span></td>
			</tr>
		</table>
		<br /><br>
	</form:form>
	
  </body>
</html>