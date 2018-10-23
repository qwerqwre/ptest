package com.yyh.pf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyh.pf.dao.MemberDao;
import com.yyh.pf.dto.Member;
import com.yyh.pf.exception.MemberNotFoundException;
import com.yyh.pf.exception.PasswordMissMatchException;
import com.yyh.pf.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired private MemberDao mDao;

	@Override
	public int join(HashMap<String, Object> memberInfo) {
		return mDao.join(memberInfo);
	}

	@Override
	public Member login(String memberId, String memberPw) throws Exception {
		Member member = mDao.findMemberId(memberId);

		if (member != null) {
			if (member.getMemberPw().equals(mDao.makeCipherText(memberPw))) {
				return member;
			} else {
				throw new PasswordMissMatchException();
			}
		} else {
			throw new MemberNotFoundException();
		}
	}

	@Override
	public int checkId(HashMap<String, String> params) {
		return mDao.checkId(params);
	}

	@Override
	public List<Map<String, Object>> selectMember(HashMap<String, String> params) {
		return mDao.selectMember(params);
	}

	@Override
	public int getTotalCount(HashMap<String, String> params) {
		return mDao.getTotalCount(params);
	}

	@Override
	public int delMember(HashMap<String, String> params) {
		return mDao.delete(params);
	}
}
