<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- tag library 선언 : c tag --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<title>게시물 상세페이지</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css" />" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
	<script type="text/javascript">
		;
		$(document).ready(function(){
			var msg = '${msg}';
			
			if(msg != ''){
				alert(msg);	
			}
			
			var readFormId = $('#readForm');
			
			$('#btnUpdate').on('click', function(){
				
				readFormId.attr("action", "<c:url value='/notice/goUpdatePage.do?typeSeq=${getBoard.type_seq}&boardSeq=${getBoard.board_seq}' />");
				readFormId.submit();
			});
			
			$('#btnDelete').on('click', function(){
				
				if(confirm("정말 삭제하시겠습니까?")){
					readFormId.attr("action", "<c:url value='/notice/delete.do' />");
					readFormId.submit();
				}
			});
		});
		
	</script>
</head>
<body>
	
	<!-- wrap -->
	<div id="wrap">

		<!-- container -->
		<div id="container">

			<!-- content -->
			<div id="content">

				<!-- title board detail -->
				<div class="title_board_detail">게시물 보기</div>
				<!-- //title board detail -->

				<!-- board_area -->
				<div class="board_area">
					<form method="post" id="readForm" name="readForm">
						<input type="hidden" name="boardSeq" value="${getBoard.get('board_seq')}" />
						<input type="hidden" name="typeSeq" value="${getBoard.get('type_seq')}" />
						<fieldset>
							<legend>Ses & Food 게시물 상세 내용</legend>

							<!-- board detail table -->
							<table summary="표 내용은 Ses & Food 게시물의 상세 내용입니다." class="board_detail_table">
								<caption>Ses & Food 게시물 상세 내용</caption>
								<colgroup>
									<col width="%" />
									<col width="%" />
									<col width="%" />
									<col width="%" />
									<col width="%" />
									<col width="%" />
								</colgroup>
								<tbody>
									<tr>
										<th class="tright">제목</th>
										<td colspan="5" class="tleft">${getBoard.get("title")}</td>
									</tr>
									<tr>
										<th class="tright">작성자</th>
										<td colspan="5" class="tleft">${getBoard.get("member_nick")}</td>
									</tr>
									<tr>
										<th class="tright">작성일</th>
										<td>
											<c:choose>
												<c:when test="${getBoard.update_date != null}">${getBoard.update_date}</c:when>
												<c:otherwise>${getBoard.create_date}</c:otherwise>
											</c:choose>
										</td>
										<th class="tright">조회수</th>
										<td class="tright">${getBoard.get("hits")}</td>
										<th class="tright">추천</th>
										<td class="tright">0</td>
									</tr>
									<tr>
										<td colspan="6" class="tleft">
											<div class="body">
												<!--<c:out value='${getBoard.get("content")}' />-->
												${getBoard.get("content")}
											</div>
										</td>
									</tr>
									<c:if test="${empty files}">
										<tr>
											<th class="tcenter">첨부파일</th>
											<td colspan="6" style="text-align:left;">
											</td>
										</tr>
									</c:if>
									<c:forEach items="${files}" var="file" varStatus="vs" >
										<tr>
											<th class="tcenter">첨부파일${vs.count}</th>
												<td colspan="6" style="text-align:left;">
													<c:choose>
														<c:when test=""${file.linked == 0}">
															${file.file_name} (서버에 파일을 찾을 수 없습니다.)
														</c:when>
													<c:otherwise>
														<a href="<c:url value='/notice/download.do?fileIdx=${file.file_idx}'/>">
														${file.file_name} (${file.file_size} bytes)
														</a>
													</c:otherwise>
													</c:choose>
												</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- //board detail table -->

							<!-- bottom button -->
							<div class="btn_bottom">
								<div class="btn_bottom_left">
									<input type="button" value="추천하기" title="추천하기" />
								</div>
								<div class="btn_bottom_right">
									<c:if test="${sessionScope.memberId != null}">
										<input type="button" id="btnUpdate" value="수정" title="수정"/>
										<input type="button" id="btnDelete" value="삭제" title="삭제"/> 
									</c:if>
									
									<input type="button" value="목록" title="목록" onclick="javascript:window.history.back();"/>
								</div>
							</div>
							<!-- //bottom button -->

						</fieldset>
					</form>
				</div>
				<!-- //board_area -->

			</div>
			<!-- //content -->

		</div>
		<!-- //container -->

	</div>
	<!-- //wrap -->

</body>
</html>