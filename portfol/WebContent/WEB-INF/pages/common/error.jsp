<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script type="text/javascript">
		var msg = '${msg}';
		var nextLocation = '${nextLocation}';
		
		function init(){
			if(msg != ''){
				var contextPath = '${pageContext.request.contextPath}';
				alert(msg);
				// 스크립트 코드를 이용한 화면이동
				window.top.location.href = contextPaht + nextLocation;
			}
		}
	</script>
</head>
<body onload="init();">
</body>
</html>