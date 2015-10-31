<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<%@ taglib prefix="atguigu" tagdir="/WEB-INF/tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(function(){
		function helloworld(){
			alert('helloworld');
		}
		
		$("button:not('#add')").click(function(){
			helloworld();
		});
		
		$("#add").click(function(){
			//在新添加 button 节点时把事件加上!
			var $btn = $("<button>Button</button>").click(function(){
				helloworld();
			});
			
			$("div").append($btn).append("<br>");
		});
	})
</script>
</head>
<body>
	
	<atguigu:helloworld count="10">
		age param value: ${param.age }
	</atguigu:helloworld>
	
	<br><br>
	
	<div>
		<button>Button</button>
		<br>
	</div>
	
	<Button id="add">Add New Button</Button>
	
</body>
</html>