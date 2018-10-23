package com.yyh.pf.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yyh.pf.dto.Member;

public interface MemberService {
	
	/**
	 * @param memberInfo
	 * @return mDao.join();
	 */
	public int join(HashMap<String, Object> memberInfo);
	
	/**
	 * @param memberId
	 * @param memberPw
	 * @return int result
	 * @throws Exception 
	 */
	public Member login(String memberId, String memberPw) throws Exception;
	
	/**
	 * 아이디 중복 체크
	 * @param params
	 * @return
	 */
	public int checkId(HashMap<String, String> params);
	
	public List<Map<String, Object>> selectMember(HashMap<String, String> params);
	
	public int getTotalCount(HashMap<String, String> params);
	
	public int delMember(HashMap<String,String> params);
}
