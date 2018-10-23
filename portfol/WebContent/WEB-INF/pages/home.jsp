<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/jquery-ui/css/jquery-ui.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/common/css/common.css" />" />

<script type="text/javascript" src="<c:url value="/resources/jquery/js/jquery-3.2.1.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery-ui/js/jquery-ui.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery/js/jquery-migrate-1.4.1.js" />"></script>

<script type="text/javascript">

$(document).ready(function(){
	//Tab
	$( "#tabs" ).tabs();
});
</script>
<title></title>
</head>
<body>

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">Intro</a></li>
	</ul>
	<div id="tabs-1">
		이 페이지는 아래 항목을 이용하여 구현하였습니다.<br/>
  		<ul class="page_desc">
			<li>Spring Framework 4.3.14.RELEASE</li>
			<li>myBatis 3.4.1</li>
			<li>jUnit 4.12</li>
		</ul>
		<br/>
		<h1>
		<a href="<c:url value='/member/goLoginPage.do' />" target="_parent">
		클릭하여 로그인 해주세요 ! ! <br/>
		관리자 - ID : qwer1234 / PW : qwer
		</a>
		</h1>
		<br/><br/>
		last update.... 2018. 10. 01
	</div>
</div>

</body>
</html>