package com.yyh.pf.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface NoticeService {
	/**
	 * 게시글 총 개수 조회하기
	 * @return COUNT(board_seq)
	 */
	public int getTotalCount();
	
	/**
	 * 게시글 전부 조회
	 * @return board table select
	 */
	public List<Map<String, Object>> selectAllList(HashMap<String, Object> params);
	
	/**
	 * @param params
	 * @return insert 실행 결과 성공 1 실패 0
	 */
	public int insertWrite(HashMap<String, Object> params, List<MultipartFile> mFiles);
	
	/**
	 * 게시글 읽기
	 * @param typeSeq
	 * @param boardSeq
	 * @return typeSeq, boardSeq
	 */
	public Map<String, Object> read(int typeSeq, int boardSeq);
	
	/**
	 * 파일 읽기
	 * @param typeSeq
	 * @param boardSeq
	 * @return typeSeq, boardSeq
	 */
	public List<HashMap<String, Object>> readFile(int typeSeq, int boardSeq);
	
	/**
	 * 게시글 삭제
	 * 첨부파일 삭제
	 * @param typeSeq
	 * @param boardSeq
	 * @param hasFile
	 * @return typeSeq, boardSeq, hasFile
	 */
	public int delete(int typeSeq, int boardSeq, String hasFile);
	
	/**
	 * 게시글 수정
	 * 첨부파일 수정
	 * @param params
	 * @param mFiles
	 * @return params
	 */
	public int update(HashMap<String, Object> params, List<MultipartFile> mFiles);
	
	/**
	 * 첨부파일 삭제
	 * @param fileIdx
	 * @param boardSeq
	 * @param typeSeq
	 * @return boolean result
	 */
	public boolean deleteAttach(int fileIdx, int boardSeq, int typeSeq);
}
