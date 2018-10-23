package com.yyh.pf.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface NoticeDao {
	/**
	 * 총 게시글 수 조회하기
	 * @return COUNT(board_seq)
	 */
	public int getTotalCount();
	
	/**
	 * 해당 게시글 전부 조회
	 * @param params
	 * @return board table select
	 */
	public List<Map<String, Object>> selectAllList(HashMap<String, Object> params);
	
	/**
	 * 글 쓰기
	 * @param params
	 * @return insert 실행 결과 성공 1 실패 0
	 */
	public int insertWrite(HashMap<String, Object> params);
	
	/**
	 * 추천수
	 * @param typeSeq
	 * @param boardSeq
	 * @return
	 */
	public int updateHits(int typeSeq, int boardSeq);
	
	/**
	 * 게시글 가져오기
	 * @param typeSeq
	 * @param boardSeq
	 * @return type_seq, board_seq
	 */
	public Map<String, Object> getBoard(int typeSeq, int boardSeq);
	
	/**
	 * 게시글 삭제
	 * @param typeSeq
	 * @param boardSeq
	 * @return type_seq, board_seq
	 */
	public int delete(int typeSeq, int boardSeq);
	
	/**
	 * 게시글 수정
	 * 첨부파일 수정
	 * @param params
	 * @return params
	 */
	public int update(HashMap<String, Object> params);
	
	/**
	 * 첨부파일 삭제(has_file이 0)
	 * @param typeSeq
	 * @param boardSeq
	 * @return type_seq, board_seq
	 */
	public int updateHasFile(int typeSeq, int boardSeq);
}
