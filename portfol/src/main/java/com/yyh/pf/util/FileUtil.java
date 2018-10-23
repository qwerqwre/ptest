package com.yyh.pf.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.yyh.pf.controller.NoticeController;

public class FileUtil {
	@Value("#{config['project.futil.path']}")
	private String saveLoaction;
	
	private Logger logger = Logger.getLogger(FileUtil.class);
	
	/**
	 * 물리적 위치에 존재하는 파일 지우기
	 * @param fileInfo
	 * @return
	 */
	public boolean deleteFile(HashMap<String, Object> fileInfo) {
		logger.debug("deleteFile saveLoaction : " + saveLoaction);
		// 파일 찾기
		File f = new File(saveLoaction, String.valueOf(fileInfo.get("fake_file_name")));
		if(f.exists()) { // 물리적 위치에 존재하면
			return f.delete(); // 지운다
		}
		return false;
	}
	
	/**
	 * 물리적 위치에 존재하는 파일을 byte 단위로 읽어오기
	 * @param fileInfo
	 * @return
	 */
	public byte[] readFile(HashMap<String, Object> fileInfo) {
		logger.debug("readFile saveLoaction : " + saveLoaction);
		// 파일 찾기
		File f = new File(saveLoaction, String.valueOf(fileInfo.get("fake_file_name")));
		System.out.println("fake_file_name : " + fileInfo.get("fake_file_name"));
		
		byte[] b = null;
		if(f.exists()) { // 물리적 위치에 존재하면
			// 파일을 byte 단위로 읽어온다
			try {
				b = FileUtils.readFileToByteArray(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // 물리적 위치에 파일이 존재하지 않음
			
		}
		
		return b;
	}
	
	/**
	 * 파일 복사,
	 * 폴더 지정,
	 * 없다면 만들어준다
	 * @param mf
	 * @param fakename
	 * @throws IOException
	 */
	public void copyFile(MultipartFile mf, String fakename) throws IOException {
		 // 폴더 지정
		 File destDir = new File(this.saveLoaction);
		 // 없다면 만든다
		 if(!destDir.exists()) {
			 destDir.mkdirs();
		 }
		 // 파일 지정
		 File destFile = new File(destDir, fakename);
		 FileCopyUtils.copy(mf.getBytes(), destFile);
	}
}
