package com.yyh.pf.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.yyh.pf.service.BoardAttachService;
import com.yyh.pf.service.NoticeService;
import com.yyh.pf.util.FileUtil;

@Controller
public class NoticeController {
	private Logger logger = Logger.getLogger(NoticeController.class);
	
	@Value("#{config['project.context.path']}")
	private String contextRoot;
	
	@Autowired NoticeService nService;
	@Autowired BoardAttachService baService;
	@Autowired FileUtil fUtil;
	
	/**
	 * 페이지 처리하는 메서드
	 * @param params
	 * @return mv
	 */
	@RequestMapping("/notice/list.do")
	public ModelAndView list(@RequestParam HashMap<String, Object> params) {
		ModelAndView mv = new ModelAndView();
		logger.debug("notice/list.do : " + params);

		// 페이징 처리
		// 현재 페이지
		// containsKey key가 있는지 검색하는 메서드
		int currentPage = (params.containsKey("currentPage")) ? Integer.parseInt((String) params.get("currentPage")) : 1;
		// 보여줄 게시글 수
		int pageArticleSize = (params.containsKey("pageArticleSize")) ? Integer.parseInt((String) params.get("pageArticleSize")) : 10;
		// 총 게시글 수
		int totalArticleSize = nService.getTotalCount();
		// 총 페이지 수
		int totalPageCnt = (int) Math.ceil((double) totalArticleSize / pageArticleSize);
		// 시작 인덱스
		int startIdx = (currentPage - 1) * pageArticleSize;
		// 끝 인덱스
		int endIdx = currentPage * pageArticleSize;
		// 블럭 수
		int pageBlockSize = 10;
		// 시작 페이지
		int pageBlockStart = (currentPage - 1) / pageBlockSize * pageBlockSize + 1;
		// 끝 페이지
		int pageBlockEnd = (currentPage - 1) / pageBlockSize * pageBlockSize + pageBlockSize;
		pageBlockEnd = (pageBlockEnd >= totalPageCnt) ? totalPageCnt : pageBlockEnd;

		params.put("startIdx", String.valueOf(startIdx));
		params.put("pageArticleSize", String.valueOf(pageArticleSize));

		// 게시글 전체를 가져온다.
		List<Map<String, Object>> allList = nService.selectAllList(params);
		mv.addObject("list", allList);
		mv.addObject("currentPage", currentPage);
		mv.addObject("pageBlockStart", pageBlockStart);
		mv.addObject("pageBlockEnd", pageBlockEnd);
		mv.addObject("pageBlockSize", pageBlockSize);
		mv.addObject("totalPageCnt", totalPageCnt);
		mv.addObject("typeSeq", params.get("typeSeq"));

		if (params.containsKey("msg")) {
			mv.addObject("msg", params.get("msg"));
		}
		mv.setViewName("/notice/list");

		return mv;
	}
	
	/**
	 * 게시글을 쓰는 페이지로 이동
	 * @return mv
	 */
	@RequestMapping("/notice/goWritePage.do")
	public ModelAndView goWritePage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/notice/write");
		return mv;
	}

	/**
	 * 게시글을 쓰는 메서드
	 * @param params
	 * @param mReq
	 * @return mv
	 */
	@RequestMapping("/notice/write.do")
	public ModelAndView write(@RequestParam HashMap<String, Object> params, MultipartHttpServletRequest mReq) {
		logger.debug("/notice/write.do : "+params);
		ModelAndView mv = new ModelAndView();

		// map 형식
		// mReq.getMultiFileMap();
		// list 형식
		List<MultipartFile> mFiles = mReq.getFiles("attFile");
		for (MultipartFile mf : mFiles) {
			if (!mf.getOriginalFilename().equals("")) {
				params.put("hasFile", "1");
				break;
			} else
				params.put("hasFile", "0");
			/*
			 * System.out.println("name : " + mf.getOriginalFilename());
			 * System.out.println("type : " + mf.getContentType());
			 * System.out.println("size : " + mf.getSize());
			 */
		}

		int result = nService.insertWrite(params, mFiles);

		RedirectView rv = new RedirectView(contextRoot+"/notice/list.do");
		mv.setView(rv);

		return mv;
	}

	/**
	 * 게시글 읽는 페이지
	 * @param params
	 * @return mv
	 */
	@RequestMapping("/notice/read.do")
	public ModelAndView read(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		logger.debug("notice/read.do : " + params);

		int typeSeq = Integer.parseInt(params.get("typeSeq"));
		int boardSeq = Integer.parseInt(params.get("boardSeq"));

		String msg = (String) params.get("msg");
		if (msg != null) {
			mv.addObject("msg", msg);
		}

		Map<String, Object> getBoard = nService.read(typeSeq, boardSeq);
		System.out.println("hasFile : " + getBoard.get("has_file"));
		// 첨부파일(hasFile)이 1이면
		if (getBoard.get("has_file").equals("1")) {
			List<HashMap<String, Object>> readFile = nService.readFile(typeSeq, boardSeq);
			mv.addObject("files", readFile);
		}

		mv.addObject("getBoard", getBoard);
		mv.setViewName("/notice/read");
		return mv;
	}

	/**
	 * 게시글 삭제하는 메서드
	 * @param typeSeq
	 * @param boardSeq
	 * @param session
	 * @return mv
	 */
	@RequestMapping("/notice/delete.do")
	public ModelAndView delete(@RequestParam int typeSeq, int boardSeq, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		if (session.getAttribute("memberId") != null) { // 로그인 체크
			// 삭제할 게시글을 가져온다
			Map<String, Object> getBoard = nService.read(typeSeq, boardSeq);
			String url = "";
			String msg = "";

			if (session.getAttribute("memberId").equals(getBoard.get("member_id"))) {
				
				String hasFile = String.valueOf(getBoard.get("has_file"));
				int result = nService.delete(typeSeq, boardSeq, hasFile);

				if (result == 1) {
					url = contextRoot+"/notice/list.do";
					msg = "삭제 성공!";
				} else {
					url = contextRoot+"/notice/read.do?typeSeq=" + typeSeq + "&boardSeq=" + boardSeq;
					msg = "삭제 실패! 관리자에게 문의하세요.";
				}

			} else { // ID 불일치
				msg = "본인이 작성한 글이 아닙니다.";
				url = contextRoot+"/board/read.do?typeSeq=" + typeSeq + "&boardSeq=" + boardSeq;
			}

			RedirectView rv = new RedirectView(url);
			mv.addObject("msg", msg);
			mv.setView(rv);

		} else { // 로그인이 안된 상태
			mv.addObject("msg", "로그인 하세요");
			mv.addObject("nextLocation", "/index.do");
			mv.setViewName("common/error");
		}

		return mv;
	}
	
	/**
	 * 게시글에 첨부 된 첨부 파일을 삭제하는 메서드
	 * @param fileIdx
	 * @param typeSeq
	 * @param boardSeq
	 * @return mv
	 */
	@RequestMapping("/notice/deleteAttach.do")
	public ModelAndView deleteAttach(@RequestParam int fileIdx, @RequestParam int typeSeq, @RequestParam int boardSeq) {
		ModelAndView mv = new ModelAndView();
		System.out.println("typeSeq : "+typeSeq);
		System.out.println("boardSeq : "+boardSeq);
		
		nService.deleteAttach(fileIdx, boardSeq, typeSeq);
		// 삭제가 되든 안 되면 /notice/update.do로 리다이렉트 한다
		RedirectView rv = new RedirectView(contextRoot+"/notice/goUpdatePage.do?typeSeq="+typeSeq+"&boardSeq="+boardSeq);
		mv.setView(rv);
		
		return mv;
	}

	/**
	 * 게시글을 수정하는 페이지로 이동
	 * @param params
	 * @param session
	 * @return mv
	 */
	@RequestMapping("/notice/goUpdatePage.do")
	public ModelAndView goUpdatePage(@RequestParam HashMap<String, String> params, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		logger.debug("/notice/goUpdatePage.do params : " + params);

		// 로그인 체크
		if (session.getAttribute("memberId") != null) { // 로그인 되어있으면
			int typeSeq = Integer.parseInt(params.get("typeSeq"));
			int boardSeq = Integer.parseInt(params.get("boardSeq"));

			Map<String, Object> getBoard = nService.read(typeSeq, boardSeq);
			
			// 작성자 ID 체크
			if (session.getAttribute("memberId").equals(getBoard.get("member_id"))) { // ID 같을 때
				mv.addObject("getBoard", getBoard);
				mv.setViewName("notice/update");
				// 첨부파일(has_file)이 1이면
				if (getBoard.get("has_file").equals("1")) { 
					List<HashMap<String, Object>> readFile = nService.readFile(typeSeq, boardSeq);
					mv.addObject("files", readFile);
				}
			} else { // ID 다를 때
				mv.addObject("msg", "수정 실패! 아이디가 다릅니다");
				RedirectView rv = new RedirectView(contextRoot+"/notice/read.do?typeSeq=" + typeSeq + "&boardSeq=" + boardSeq);
				mv.setView(rv);
			}
		} else { // 로그인 안 되어있으면
			mv.addObject("msg", "로그인 하세요");
			// nextLocation 이라는 다음 위치를 지정
			mv.addObject("nextLocation", "/index.do");
			// common의 error 로 보낸다
			mv.setViewName("common/error");
		}
		return mv;
	}

	/**
	 * 게시글을 수정하는 메서드,
	 * 로그인 확인 (DB에 저장된 ID와 사용자가 입력한 ID가 일치하는지),
	 * 로그인 안 되어있으면 -> index로
	 * has_file 수정(삭제, 파일 넣기)
	 * @param params
	 * @param session
	 * @param mReq
	 * @return mv
	 */
	@RequestMapping("/notice/update.do")
	public ModelAndView update(@RequestParam HashMap<String, Object> params, HttpSession session, MultipartHttpServletRequest mReq) {
		ModelAndView mv = new ModelAndView();
		logger.debug("notice/update.do : " + params);

		// 로그인 확인
		if (session.getAttribute("memberId") != null) { // 로그인 했을 때
			// read() 가져오고
			int typeSeq = Integer.parseInt((String) params.get("typeSeq"));
			int boardSeq = Integer.parseInt((String) params.get("boardSeq"));
			// 작성자 일치하면 수정
			Map<String, Object> getBoard = nService.read(typeSeq, boardSeq);
				if(session.getAttribute("memberId").equals(getBoard.get("member_id"))) {
					// <input type="file" -> 존재 해야만 값이 반환됨
					List<MultipartFile> files = mReq.getFiles("attFile");
					// has_file 수정
					if(params.get("hasFile").equals("0")) { // has_file이 0이면
						for(MultipartFile m : files) { 
							if(!m.getOriginalFilename().equals("")) { // 파일의 원래 이름이 빈열이 아니라면
								params.put("hasFile", "1"); // has_file은 1
							} else { // 아니면 has_file은 0
								params.put("hasFile", "0");
							}
						}
					}
					int update = nService.update(params, files);
					RedirectView rv = new RedirectView(contextRoot+"/notice/read.do?boardSeq="+boardSeq+"&typeSeq="+typeSeq);
					mv.setView(rv);
				} else {
					RedirectView rv = new RedirectView(contextRoot+"/notice/read.do?typeSeq="+typeSeq+"&boardSeq="+boardSeq);
					mv.addObject("msg", "수정 실패! 아이디가 다릅니다.");
					mv.setView(rv);
				}
			} else {
			mv.setViewName("common/error");
			mv.addObject("msg", "로그인 하세요.");
			mv.addObject("nextLocation", "/index.do");
		}
		return mv;
	}
	
	/**
	 * 첨부 파일 다운로드,
	 * 한글 파일 인코딩
	 * @param fileIdx
	 * @param rep
	 * @return byte[] b
	 */
	@ResponseBody
	@RequestMapping("/notice/download.do")
	public byte[] download(@RequestParam int fileIdx, HttpServletResponse rep) {
		logger.debug("/notice/download.do params : " + fileIdx);
		// PK를 이용하여 참부파일 정보를 가져온다
		// BoardAttachService/구현체, PK로 select하는 sql 만들기
		HashMap<String, Object> fileInfo = baService.getAttchFile(fileIdx);
		byte[] b = null;
		// 한글 파일명 인코딩
		String endcodingName = null;
		try {
			endcodingName = java.net.URLEncoder.encode(fileInfo.get("file_name").toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(fileInfo == null) { // 지워짐
			
		} else { // 지워짐
			// 첨부파일 정보를 토대로 파일을 읽어와 돌려보낸다
			b = fUtil.readFile(fileInfo);
			
			// 돌려보내기 위해 응답(HttpServletResponse)에 정보를 입력한다
			// 파일 다운로드를 할 수 있는 정보들을 브라우저에 알려주는 역할(정보 전달)
							// 내용의 성질				
			rep.setHeader("Content-Disposition", "attachment; filename=\"" + endcodingName + "\"");
			rep.setContentType(String.valueOf(fileInfo.get("file_type")));
			rep.setHeader("Pragma", "no-cache");
			rep.setHeader("Cache-Control", "no-cache"); // cache를 쓰면 똑같은 파일 안 생김
			String tmp = String.valueOf(fileInfo.get("file_size"));
			rep.setContentLength(Integer.parseInt(tmp));
		}
		
		return b;
	}
}
