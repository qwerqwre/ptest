package com.yyh.pf.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BoardAttachDao {
	/**
	 * board_attach에 파일 정보 입력
	 * @param params
	 * @return params
	 */
	public int insertBoardAtt(HashMap<String, Object> params);
	
	/**
	 * board_attach 에서 파일 가져오기
	 * @param typeSeq
	 * @param boardSeq
	 * @return typeSeq, boardSeq
	 */
	public List<HashMap<String, Object>> getFile(int typeSeq, int boardSeq);
	
	/**
	 * DB에 저장 된 fileIdx 가져오기
	 * @param fileIdx
	 * @return file_idx
	 */
	public HashMap<String, Object> getAttachFile(int fileIdx);
	
	/**
	 * DB에 있는 파일 삭제
	 * @param fileIdx
	 * @return file_idx
	 */
	public int deleteAttach(int fileIdx);
	
	/**
	 * 글번호, 타입으로 첨부파일 삭제
	 * @param boardSeq
	 * @param typeSeq
	 * @return board_seq, board_type
	 */
	public int deleteNumType(int boardSeq, int typeSeq);
}
