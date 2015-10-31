
<%--  
1. 为什么会使用 tag 文件来定义自定义标签: 因为在 SimpleTagSupport 的子类中大量输出 JS 代码是非常不方便的!
2. 如何定义呢 ? 在 WEB-INF 目录下新建 tags 文件夹, 在 tags 或其子目录下新建 tag 文件
3. tag 文件类似于 jsp 文件, 但有如下的不同
1). 在 tag 文件内部可使用 SimpleTagSupport 的 API
2). 在 tag 文件内部使用 tag 指令来定义标签的公共属性. 例如: 是否有标签体, 编码方式等
3). 在 tag 文件内容使用 attribute 属性来定义标签的个性化属性. 
4. 在 JSP 页面上如何使用 tag 定义的标签呢 ?
导入: 
<%@ taglib prefix="atguigu" tagdir="/WEB-INF/tags" %> 
正常使用. 
5. 可以像 JSP 页面那样去引用其他的标签库. 

使用 tag 文件定义了翻页标签!
--%>


<!-- 使用 tag 指定来定义(所有标签都有的)标签的属性! -->
<%@ tag import="java.util.Date"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="scriptless" %>

<!-- 定义当前标签的属性. -->
<!-- 
name: 属性名 
required: 该属性是否为必须的.
rtexprvalue: rt-runtime expr:expression value:value 是否可以接受运行时表达式的值
-->
<%@ attribute name="count" required="true" rtexprvalue="true" %>

HelloWorld: <%= new Date() %>
<br><br>

count: <%= count %>

<!-- 处理标签体 -->
<br><br>
<%
	//在 SimpleTagSupport 中如何输入标签体内容!
	getJspBody().invoke(null);
%>