package com.yyh.pf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyh.pf.dao.BoardDao;
import com.yyh.pf.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired private BoardDao bDao;
	
	@Override
	public int insertWrite(HashMap<String, String> params) {
		return bDao.insertWrite(params);
	}

	@Override
	public List<Map<String, Object>> selectAllList(HashMap<String, String> params) {
		return bDao.selectAllList(params);
	}

	@Override
	public Map<String, Object> read(int typeSeq, int boardSeq) {
		bDao.updateHits(typeSeq, boardSeq);
		
		return bDao.getBoard(typeSeq, boardSeq);
	}

	@Override
	public int delete(int typeSeq, int boardSeq) {
		return bDao.delete(typeSeq, boardSeq);
	}

	@Override
	public int update(HashMap<String, Object> params) {
		
		return bDao.update(params);
	}

	@Override
	public int getTotalCount(HashMap<String, String> params) {
		
		return bDao.getTotalCount(params);
	}

	@Override
	public List<Map<String, Object>> selectBoard(HashMap<String, String> params) {
		
		return bDao.selectBoard(params);
	}
}
