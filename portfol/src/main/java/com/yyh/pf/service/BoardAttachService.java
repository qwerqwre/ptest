package com.yyh.pf.service;

import java.util.HashMap;
import java.util.List;

public interface BoardAttachService {
	/**
	 * DB에 저장되어있는 파일 정보 불러오기
	 * @param fileIdx
	 * @return
	 */
	public HashMap<String, Object> getAttchFile(int fileIdx);
	
	/**
	 * 첨부파일 삭제
	 * @param fileIdx
	 * @return
	 */
	public int deleteAttach(int fileIdx);
}
