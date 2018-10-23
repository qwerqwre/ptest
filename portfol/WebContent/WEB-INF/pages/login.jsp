<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- tag library 선언 : c tag --%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title>로그인 & 회원가입</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/login.css" />" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var panelOne = $('.form-panel.two').height(), 
			panelTwo = $('.form-panel.two')[0].scrollHeight;
	
		$('.form-panel.two').not('.form-panel.two.active').on('click', function(e) {
			e.preventDefault();
	
		    $('.form-toggle').addClass('visible');
		    $('.form-panel.one').addClass('hidden');
		    $('.form-panel.two').addClass('active');
		    $('.form').animate({
		      'height': panelTwo
		    }, 200);
		});
	
		$('.form-toggle').on('click', function(e) {
		    e.preventDefault();
		    $(this).removeClass('visible');
		    $('.form-panel.one').removeClass('hidden');
		    $('.form-panel.two').removeClass('active');
		    $('.form').animate({
		      'height': panelOne
		    }, 200);
		});
		
		$('#btnRegister').on('click', function() {
			var joinForm = document.joinForm;
			var memberId = joinForm.memberId.value;
			var memberPw = joinForm.memberPw.value;
			var memberPw2 = document.getElementById("JmemberPw2").value;
			var memberName = joinForm.memberName.value;
			var memberNick = joinForm.memberNick.value;
			var email = joinForm.email.value;
			var birthDate = joinForm.birthDate.value;
			
			
			var regExp = /^([_0-9a-zA-Z]){6,12}$/i;
			if(memberId == null || memberId == ""){
				alert("아이디를 입력하세요.");
				return joinForm.memberId.focus();
			}
			if(memberId.match(regExp) == null){
				alert("아이디는 6자 ~ 12자 (특수문자는 -, _만 가능)");
				return joinForm.memberId.focus();
			}
			
			if(memberId.length < 7  || memberId.length > 12){
				alert("아이디는 7자 ~ 12자 사이만 가능합니다.");
				return joinForm.memberId.focus();
			}
			
			
			if(memberPw == null || memberPw == ""){
				alert("비밀번호를 입력하세요.");
				return joinForm.memberPw.focus();
			}
			
			if(memberPw != memberPw2){
				alert("비밀번호가 일치하지 않습니다.");
				return document.getElementById("JmemberPw2").focus();
			}
			
			if(memberName == null || memberName == ""){
				alert("이름을 입력하세요.");
				return joinForm.memberName.focus();
			}
			
			if(memberNick == null || memberNick == ""){
				alert("닉네임을 입력하세요.");
				return joinForm.memberNick.focus();
			}
			
			if(email == null || email == ""){
				alert("이메일을 입력하세요.");
				return joinForm.userNick.focus();
			}
			var regExp =
				// 숫자or영대소문자@숫자or영대소문자.com
				/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{3,4}$/i;
			if(email.match(regExp) == null){
				alert("Invalid Forma. You Should follow this format. \nex: sample");
				$('#email').focus();
				return;
			}
			
			if(birthDate == null || birthDate == ""){
				alert("생일을 입력하세요.");
				return joinForm.birthDate.focus();
			}
			
			// 회원가입 요청 전 비동기 통신으로 ID 중복 검사
			var resultCnt = 0;
			
			$.ajax({
				url : '<c:url value="/member/checkId.do"/>',
				// url에 보낼 데이터(파라미터)
				data : { memberId: $('#JmemberId').val() },
				// '통신'이 성공하면 success
				success : function(result, textStatus, XMLHttpRequest){
					alert('값 도착');
					if(result == 0){
						resultCnt = result;		
						// 회원가입 요청
						joinForm.action = "<c:url value='/member/join.do'/>";
						joinForm.method = "post";
						joinForm.submit();
					} else {
						alert('중복된 아이디입니다.');
					}
				},
				// success 끝
				error : function(e){
					alert(e);
				}
				// error 끝
			});
			
			alert('비동기 통신 호출 함');
		});
	});
	
</script>
</head>
<body>
	<!-- 디자인 출처 : http://www.blueb.co.kr/?c=2/34&where=subject%7Ctag&keyword=%EB%A1%9C%EA%B7%B8%EC%9D%B8&uid=4050 -->
	<!-- Form-->
	<div class="form">
		<div class="form-toggle"></div>
		<div class="form-panel one">
			<div class="form-header">
				<h1>Account Login</h1>
				<c:if test="${msg != null || msg != ''}">
					<b><font color="red">${msg}</font></b>
				</c:if>
			</div>
			<!-- 로그인 -->
			<div class="form-content">
				<form action="<c:url value='/member/login.do'/>" method="post">
					<input type="hidden" name="cmd" value="login" />
					<div class="form-group">
						<label for="membername">member Id</label> 
						<input type="text" id="LmemberId" name="memberId" required="required" />
					</div>
					<div class="form-group">
						<label for="password">Password</label> 
						<input type="password" id="LmemberPw" name="memberPw" required="required" />
					</div>
					<div class="form-group">
						<label class="form-remember"> 
							<input type="checkbox" />Remember Me
						</label>
						<a href="#" class="form-recovery">Forgot Password?</a>
					</div>
					<div class="form-group">
						<button type="submit">Log In</button>
					</div>
				</form>
			</div>
		</div>
		
		<!-- 회원가입 -->
		<div class="form-panel two">
			<div class="form-header">
				<h1>Register Account</h1>
			</div>
			<div class="form-content">
				<form name="joinForm">
					<input type="hidden" name="cmd" value="join" />
					<div class="form-group">
						<label for="membername">member Id</label> 
						<input type="text" id="JmemberId" name="memberId" required="required" />
					</div>
					<div class="form-group">
						<label for="password">Password</label> 
						<input type="password" id="JmemberPw" name="memberPw" required="required" />
					</div>
					<div class="form-group">
						<label for="cpassword">Confirm Password</label> 
						<input type="password" id="JmemberPw2" required="required" />
					</div>
					<div class="form-group">
						<label for="name">Name</label> 
						<input type="text" id="JmemberName" name="memberName" required="required" />
					</div>
					<div class="form-group">
						<label for="nickname">Nickname</label> 
						<input type="nick" id="JmemberNick" name="memberNick" required="required" />
					</div>
					<div class="form-group">
						<label for="email">e-mail</label> 
						<input type="email" id="Jemail" name="email" required="required" />
					</div>
					<div class="form-group">
						<label for="birthDate">Birth Date(yyyy-mm-dd)</label> 
						<input type="birthDate" id="JbirthDate" name="birthDate" required="required" />
					</div>
					<div class="form-group">
						<button type="button" id="btnRegister">Register</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>