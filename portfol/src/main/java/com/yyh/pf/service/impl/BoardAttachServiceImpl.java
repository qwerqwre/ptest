package com.yyh.pf.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyh.pf.dao.BoardAttachDao;
import com.yyh.pf.service.BoardAttachService;

@Service
public class BoardAttachServiceImpl implements BoardAttachService{
	@Autowired private BoardAttachDao baDao;
	private String saveLoaction;

	@Override
	public HashMap<String, Object> getAttchFile(int fileIdx) {
		return baDao.getAttachFile(fileIdx);
	}

	@Override
	public int deleteAttach(int fileIdx) {
		return baDao.deleteAttach(fileIdx);
	}
}
