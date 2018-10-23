package com.yyh.pf.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yyh.pf.dao.BoardAttachDao;
import com.yyh.pf.dao.NoticeDao;
import com.yyh.pf.service.NoticeService;
import com.yyh.pf.util.FileUtil;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired NoticeDao nDao;
	@Autowired BoardAttachDao baDao;
	@Autowired FileUtil fUtil;

	@Override
	public int getTotalCount() {
		return nDao.getTotalCount();
	}

	@Override
	public List<Map<String, Object>> selectAllList(HashMap<String, Object> params) {
		return nDao.selectAllList(params);
	}

	@Override
	public int insertWrite(HashMap<String, Object> params, List<MultipartFile> mFiles) {
		// 1. 글 등록
		nDao.insertWrite(params);
		
		System.out.println("boardSeq : " + params.get("boardSeq"));
		// 2. 첨부파일이 있으면 board_attach 테이블에 등록
		for(MultipartFile mf : mFiles) {
			if(!mf.getOriginalFilename().equals("")) { // 파일이 존재하면
				// 난수를 만들어 가짜 이름으로 사용..
				String fakename = UUID.randomUUID().toString().replace("-", "");
				params.put("fileName", mf.getOriginalFilename());
				params.put("fakeFileName", fakename);
				params.put("fileType", mf.getContentType());
				params.put("fileSize", mf.getSize());
				baDao.insertBoardAtt(params);
				try {
					fUtil.copyFile(mf, fakename);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		return 0;
	}

	@Override
	public Map<String, Object> read(int typeSeq, int boardSeq) {
//		nDao.updateHits(typeSeq, boardSeq);
		Map<String, Object> result = nDao.getBoard(typeSeq, boardSeq);
		System.out.println("result : " + result);
		return result;
	}

	@Override
	public List<HashMap<String, Object>> readFile(int typeSeq, int boardSeq) {
		return baDao.getFile(typeSeq, boardSeq);
	}

	@Override
	public int delete(int typeSeq, int boardSeq, String hasFile) {
		int result = 0;
		if(hasFile.equals("1")) {
			// 1. 삭제할 첨부파일 정보를 모두 가지고 온다
			List<HashMap<String, Object>> files = baDao.getFile(typeSeq, boardSeq);
			// 글번호와 타입으로 첨부 파일을 삭제하는 DAO 메서드 호출
			baDao.deleteNumType(boardSeq, typeSeq);
			// 글 삭제
			result = nDao.delete(typeSeq, boardSeq);
			// 물리적 위치에서 삭제
			for(HashMap<String, Object> file : files) {
				fUtil.deleteFile(file);
			}
			return result;
		} 
		return nDao.delete(typeSeq, boardSeq);
	}

	@Override
	public int update(HashMap<String, Object> params, List<MultipartFile> mFiles) {
		for(MultipartFile mf : mFiles) {
			if(!mf.getOriginalFilename().equals("")) {
				// 난수를 만들어 가짜 이름으로 사용..
				String fakename = UUID.randomUUID().toString().replace("-", "");
				params.put("typeSeq", 1);
				params.put("fileName", mf.getOriginalFilename());
				params.put("fakeFileName", fakename);
				params.put("fileType", mf.getContentType());
				params.put("fileSize", mf.getSize());
				baDao.insertBoardAtt(params);
				
				try {
					fUtil.copyFile(mf, fakename);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		return nDao.update(params);
	}

	@Override
	public boolean deleteAttach(int fileIdx, int boardSeq, int typeSeq) {
		boolean result = false;
		// 첨부파일 정보를 가져온다
		HashMap<String, Object> fileInfo = baDao.getAttachFile(fileIdx);
		// DB에서 첨부파일 정보를 삭제한다
		result = (baDao.deleteAttach(fileIdx) == 1);
		// 원 게시글과 첨부파일 정보의 관계를 확인한다
		List<HashMap<String, Object>> files = baDao.getFile(typeSeq, boardSeq);
		// 가져온 첨부파일이 없으면(더 이상 첨부파일이 없으면)
		if(files == null || files.size() == 0) {
			// 게시글의 has_file을 0으로 바꾼다
			result = (nDao.updateHasFile(typeSeq, boardSeq) == 1 && result);
		}
		// 물리 디스크에서 삭제한다 (fake_file_name)
		result = (fUtil.deleteFile(fileInfo) == result);
		
		return result;
	}
}
