package com.yyh.pf.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BoardDao {
	/**
	 * @param params
	 * @return insert 실행 결과 성공 1 실패 0
	 */
	public int insertWrite(HashMap<String, String> params);
	
	/**
	 * 게시글 전체 조회
	 * @return 
	 */
	public List<Map<String, Object>> selectAllList(HashMap<String, String> params);
	
	/**
	 * 총 게시글 수 가져오기
	 * @param params
	 * @return
	 */
	public int getTotalCount(HashMap<String, String> params);
	
	/**
	 * 조회수 올려주기
	 * @param typeSeq
	 * @param boardSeq
	 * @return
	 */
	public int updateHits(int typeSeq, int boardSeq);
	
	/**
	 * 게시글 1건 조회
	 * @param typeSeq
	 * @param boardSeq
	 * @return
	 */
	public Map<String, Object> getBoard(int typeSeq, int boardSeq);
	
	/**
	 * 게시글 삭제
	 * @param typeSeq
	 * @param boardSeq
	 * @return
	 */
	public int delete(int typeSeq, int boardSeq);
	
	/**
	 * 게시글 수정
	 * @param params
	 * @return
	 */
	public int update(HashMap<String, Object> params);
	
	public List<Map<String, Object>> selectBoard(HashMap<String, String> params);
}
