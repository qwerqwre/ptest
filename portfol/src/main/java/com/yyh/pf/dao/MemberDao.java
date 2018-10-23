package com.yyh.pf.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yyh.pf.dto.Member;

public interface MemberDao {
	/**
	 * @param HashMap<String, Object> memberInfo
	 * @return jdbcTemplate.update();
	 */
	public int join(HashMap<String, Object> memberInfo);
	
	/**
	 * @param memberId
	 * @return MemberDto mDto || null
	 */
	public Member findMemberId(String memberId);
	
	/**
	 * @param memberPw
	 * @return String jdbcTemplate.queryForObject()
	 */
	public String makeCipherText(String memberPw);
	
	/**
	 * 아이디 중복 체크
	 * @param params
	 * @return 1, 0
	 */
	public int checkId(HashMap<String, String> params);
	
	public List<Map<String, Object>> selectMember(HashMap<String, String> params);
	
	public int getTotalCount(HashMap<String, String> params);
	
	public int delete(HashMap<String, String> params);
}
