<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- tag library 선언 : c tag --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<title>게시물 작성하기</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css" />" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
	<script src="https://cdn.ckeditor.com/ckeditor5/11.0.1/classic/ckeditor.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#btnWrite').on('click', function(){
				
				var titleName = $('input[name=title]');
				
				if(titleName.val().length == 0){
					alert("제목을 입력해주세요.");
					titleName.focus();
					return;
				}
				
				//var contents = frm.contents.value;
				// frm.contents.value = ckEditor.getData();
				var contents = ckEditor.getData();
				
				if(contents.length == 0){
					alert("내용을 입력해주세요.");
					$('#contents').focus();
					return;
				}
				
				var writeFormId = $('#writeForm');
				writeFormId.attr("action", "<c:url value='/notice/write.do'/>");
				writeFormId.submit();
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

				<!-- title board write -->
				<div class="title_board_write">글쓰기</div>
				<!-- //title board write -->

				<!-- board_area -->
				<div class="board_area">
					<form id="writeForm" name="writeForm" method="post" enctype="multipart/form-data">
						<fieldset>
							<legend>Ses & Food 글쓰기</legend>

							<!-- board write table -->
							<table summary="표 내용은 Ses & Food 글쓰기 박스입니다." class="board_write_table">
								<caption>Ses & Food 글쓰기 박스</caption>
								<colgroup>
									<col width="20%" />
									<col width="80%" />
								</colgroup>
								<tbody>
									<tr>
										<th class="tright">
											<label for="board_write_name">이름</label>
										</th>
										<td class="tleft">
											<input type="hidden" name="typeSeq" value="1" />
											<input type="hidden" name="memberIdx" value="${sessionScope.memberIdx}" />
											<input type="hidden" name="memberId" value="${sessionScope.memberId}" />
											<input type="text" id="memberNick" name="memberNick" title="이름 입력박스" class="input_100" readonly="readonly" value="${sessionScope.memberNick}"/>
										</td>
									</tr>
									<tr>
										<th class="tright"><label for="board_write_title">제목</label></th>
										<td class="tleft">
											<input type="text" name="title" title="제목 입력박스" class="input_380" />
										</td>
									</tr>
									<tr>
										<th class="tright">
											<label for="board_write_title">내용</label>
										</th>
										<td class="tleft">
											<div class="editer">
												<p>
													<textarea rows="20" cols="100" id="contents" name="contents"></textarea>
													<script>
															var chkEditor;
													    ClassicEditor
													    .create( document.querySelector( '#contents' ) )
													    .then( editor => {
													    	ckEditor = editor;
													    })
													    .catch( error => {
													    	console.error( error );
													    });
													</script>
												</p>
											</div>
										</td>
									</tr>
									<tr>
										<th class="tright"><label for="board_write_title">첨부파일</label></th>
											<td class="tleft">
												<input type="file" name="attFile" />
											</td>
									</tr>
								</tbody>
							</table>
							<!-- //board write table -->

							<!-- bottom button -->
							<div class="btn_bottom">
								<div class="btn_bottom_right">
									<input type="button" value="작성취소" title="작성취소" onclick="javascript:window.history.back();"/>
									<input type="button" value="완료" id="btnWrite" title="완료"/>
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