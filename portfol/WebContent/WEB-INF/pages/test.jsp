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
<script type="text/javascript" src="<c:url value="/resources/jquery-ui/js/i18n/datepicker-ko.js" />"></script>

<script type="text/javascript">

$(document).ready(function(){
	// 셀렉트 박스 생성을 위한 ajax 호출
	$.ajax({
		url: '<c:url value="/dept/getSelectBoxData.do" />',
		data : { },
		success : function (depts, textStatus, XMLHttpRequest) {
			// depts : controller에서 return 해준 값 = 부서정보 
			var select = $('#deptNos');
			var option = $('<option>').attr({'name' : 'deptNo', 'value' : ''}).text("-선택하세요-");
			$(select).append(option);
			$.each(depts, function(key, dept){
				// $('<option>') 로 태그 생성
				// attr : attribute (속성) 지정
				// text 화면에 보여줄 텍스트 
				option = $('<option>').attr({'name' : 'deptNo', 'value' : dept.deptNo}).text(dept.deptName);
				// <option name="deptNo" value="d001"> Finance </option> 
				
				$("#deptNos").append(option);
			});
		},
		error : function (XMLHttpRequest, textStatus, errorThrown) {
			alert('에러 \n'+XMLHttpRequest);
		}
	});
	// ajax 끝
	
	
	$('#btnGetValue01').click(function(){
		
		var deptNo = $('#deptNos option:selected').val();
		var deptName = $('#deptNos option:selected').text();
		alert('선택한 값 : ' + deptNo + ', 부서명 : ' + deptName);
		
	});
	
	// check box 박스 생성을 위한 ajax 호출
	$.ajax({
		url: '<c:url value="/test/getCheckBoxData.do" />',
		data : { },
		success : function (hobbies, textStatus, XMLHttpRequest) {
			// hobbies : controller에서 return 해준 값 = 부서정보 
			
			var label = null;
			var text = null;
			
			$.each(hobbies, function(key, data){
				label = $('<label>').attr({ 'for' : data.hobby }).text(data.hobby);
				// <label for="h001">여행</label>
				$(label).insertAfter($('#chkBoxFieldSet legend'));
				
				// $('<input>') 로 태그 생성, attr : attribute (속성) 지정
				input = $('<input>').attr({'type' : 'checkbox', 'name':'hobby', 'title':data.hobby, 'value' : data.code});
				// <input type="checkbox" name="hobby" value="h001" />

				$(input).insertAfter($('#chkBoxFieldSet legend'));
			});
			
			// 값이 h004인 checkbox 선택하기
			$('input[name=hobby]').filter('[value=h004]').prop('checked', true);
		},
		error : function (XMLHttpRequest, textStatus, errorThrown) {
			alert('에러 \n'+XMLHttpRequest);
		}
	});
	// ajax 끝
	
	
	$('#btnGetValue02').click(function(){
		// 1건 값
		var code = $('input[name=hobby]:checked').val();
		
		// 여러건, get 방식
		var codes = $('input[name=hobby]:checked').serialize();
		
		// 여러건, 배열에 값만 넣기
		var codeArray = [];
		$.each($('input[name=hobby]:checked'), function(){
			codeArray.push($(this).val());
		});
		
		var hobby = $('input[name=hobby]:checked').attr('title');
		alert('코드 : ' + codeArray + ', 취미 : ' + hobby);
		
	});
	
	// 나라 설정
	$.datepicker.setDefaults( $.datepicker.regional['ko'] );
	// datepicker
	$( "#datepicker" ).datepicker(
			{
				defaultDate : "+1w",
				changeMonth : true,
				changeYear : true,
				minDate : "+0D",
				dateFormat : "yy-mm-dd"
			}
	);
	
	$('#btnGetValue03').click(function(){
		
		var deptNo = $('#deptNos option:selected').val();
		var deptName = $('#deptNos option:selected').text();
		alert('선택한 값 : ' + deptNo + ', 부서명 : ' + deptName);
		
	});
	
	// Tab
	$( "#tabs" ).tabs();
});
</script>
<title></title>
</head>
<body>

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">Test</a></li>
	</ul>
	<div id="tabs-1">
		<form name="testForm">
			<fieldset>
			    <legend>동적으로 Select box 생성</legend>
			    <label>부서 : </label>
			    <select id="deptNos" name="deptNos"></select>
			    <button id="btnGetValue01" type="button">값 보기</button>
			</fieldset>
			<br/>
			<fieldset id="chkBoxFieldSet">
			    <legend>동적으로 Check box 생성</legend>
			    <button id="btnGetValue02" type="button">값 보기</button>
			</fieldset>
			<br/>
			<fieldset>
			    <legend>jQuery datepicker</legend>
			    <label>부서 : </label>
			    <input type="text" id="datepicker" name="datepicker"/>
			    <button id="btnGetValue03" type="button">값 보기</button>
			</fieldset>
			<br/>
		</form>
	</div>
</div>

</body>
</html>