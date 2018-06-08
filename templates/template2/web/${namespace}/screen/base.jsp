<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<%@ include file="/commons/meta.jsp" %>
	<rapid:block name="head"/>
</head>
<body style="background-color: #ecf0f5;">
	<%@ include file="/commons/messages.jsp" %>
	<div class="wrapper" id="operation">
	
		<%@ include file="include/header.jsp" %>
		<!-- 导航 -->
		<rapid:block name="content_header"/>
		
		<!-- 正文 -->
		<section class="content">
		<div class="row">
	        <div class="col-xs-12">
				<rapid:block name="content"/>
				
			</div>
		</div>
		</section>
	</div>
	<!-- 底部 js -->
	<%@ include file="include/footer.jsp" %>
	<rapid:block name="footer"/>
</body>
</html>