/** 
 * 모든 페이지에 공통으로 사용되는 스크립트
 * */

/* jqGrid 화면 사이즈에 맞게 크기 조정 */
function gridResize(gridId){
	var gridParentWidth = $('#gbox_' + gridId).parent().width();
	$('#' + gridId).jqGrid('setGridWidth', gridParentWidth);
};