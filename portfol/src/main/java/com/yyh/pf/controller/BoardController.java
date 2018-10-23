package com.yyh.pf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.yyh.pf.service.BoardService;

@Controller
public class BoardController {
private Logger logger = Logger.getLogger(BoardController.class);
	
	@Value("#{config['project.context.path']}")
	private String contextRoot;
	
	@Autowired private BoardService bService;
	
	@RequestMapping("/board/list.do")
	public ModelAndView list(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		System.out.println("params : " + params);
		// 페이징 처리
		// 현재 페이지
		// containsKey key가 있는지 검색하는 메서드
		int currentPage = (params.containsKey("currentPage")) ? Integer.parseInt(params.get("currentPage")) : 1;
		// 보여줄 게시글 수
		int pageArticleSize = (params.containsKey("pageArticleSize")) ? Integer.parseInt(params.get("pageArticleSize")) : 10;
		// 총 게시글 수
		int totalArticleSize = bService.getTotalCount(params);
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
		List<Map<String, Object>> allList = bService.selectAllList(params);
		logger.debug("board/list.do params allList : " + allList);
		mv.addObject("list", allList);
		mv.addObject("currentPage", currentPage);
		mv.addObject("pageBlockStart", pageBlockStart);
		mv.addObject("pageBlockEnd", pageBlockEnd);
		mv.addObject("pageBlockSize", pageBlockSize);
		mv.addObject("totalPageCnt", totalPageCnt);
		mv.addObject("typeSeq", params.get("typeSeq"));
		mv.addObject("searchType", params.get("searchType"));
		mv.addObject("searchText", params.get("searchText"));
		
		
		if(params.containsKey("msg")) {
			mv.addObject("msg", params.get("msg"));
		}
		
		mv.setViewName("/board/list");
		return mv;
	}
	
	@RequestMapping("/board/goWritePage.do")
	public ModelAndView goWirtePage() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/board/write");
		
		return mv;
	}
	
	@RequestMapping("/board/wirte.do")
	public ModelAndView write(@RequestParam HashMap<String, String> params) {
		logger.debug("/board/wirte.do : "+params);
		ModelAndView mv = new ModelAndView();
		
//		String contents = params.get("contents");
//		contents = contents.replaceAll("\\r", "<br>");
//		contents = contents.replaceAll("\\n", "<br>");
//		contents = contents.replaceAll("\\r\\n", "<br>");
//		params.put("contents", contents);
		
		int result = bService.insertWrite(params);
		
		RedirectView rv = new RedirectView(contextRoot+"/board/gList.do");
		mv.setView(rv);
		
		return mv;
	}
	
	@RequestMapping("/board/read.do")
	public ModelAndView read(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		logger.debug("/board/read.do params : " + params);

		int typeSeq = Integer.parseInt(params.get("typeSeq"));
		int boardSeq = Integer.parseInt(params.get("boardSeq"));
		
		String msg = params.get("msg");
		if(msg != null) {
			mv.addObject("msg", msg);
		}
		
		Map<String, Object> getBoard = bService.read(typeSeq, boardSeq);
		
		mv.addObject("getBoard", getBoard);
		mv.setViewName("/board/read");
		return mv;
	}
	
	@RequestMapping("/board/delete.do")
	public ModelAndView delete(@RequestParam int typeSeq, int boardSeq, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		if(session.getAttribute("memberId") != null) {	// 로그인 체크
			Map<String, Object> getBoard = bService.read(typeSeq, boardSeq);
			String url = "";
			String msg = "";
			
			if(session.getAttribute("memberId").equals(getBoard.get("member_id"))) {
				
				int result = bService.delete(typeSeq, boardSeq);
				
				if(result == 1) {
					url = contextRoot+"/board/gList.do";
					msg = "삭제 성공!";
				} else {
					url = contextRoot+"/board/read.do?typeSeq="+typeSeq+"&boardSeq="+boardSeq;
					msg = "삭제 실패!! 관리자에게 문의하세요.";
				}
				
			} else {
				msg = "본인이 작성한 글이 아닙니다.";
				url = contextRoot+"/board/read.do?typeSeq="+typeSeq+"&boardSeq="+boardSeq;
			}
			
			RedirectView rv = new RedirectView(url);
			mv.addObject("msg", msg);
			mv.setView(rv);
			
		} else {	// 로그인이 안된 상태
			mv.addObject("msg", "로그인 하세요");
			mv.addObject("nextLocation", "/index.do");
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	@RequestMapping("/board/goUpdatePage.do")
	public ModelAndView goUpdatePage(@RequestParam HashMap<String, String> params, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		logger.debug("/board/goUpdateParge.do params: " + params);
		
		if(session.getAttribute("memberId") != null) {	// 로그인 체크
			int typeSeq = Integer.parseInt(params.get("typeSeq"));
			int boardSeq = Integer.parseInt(params.get("boardSeq"));
			
			Map<String, Object> getBoard = bService.read(typeSeq, boardSeq);
			
			if(session.getAttribute("memberId").equals(getBoard.get("member_id"))) {	// 작성자 ID 체크
				mv.addObject("getBoard", getBoard);
				mv.setViewName("board/update");
			} else {
				mv.addObject("msg", "본인이 작성한 글이 아닙니다.");
				RedirectView rv = new RedirectView(contextRoot+"/board/read.do?typeSeq="+typeSeq+"&boardSeq="+boardSeq);
				mv.setView(rv);
			}
			
		} else {	// 로그인이 안된 상태
			mv.addObject("msg", "로그인 하세요");
			mv.addObject("nextLocation", "/index.do");
			mv.setViewName("common/error");
		}
		
		return mv;
	}
	
	@RequestMapping("/board/update.do")
	public ModelAndView update(@RequestParam HashMap<String, Object> params, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		String msg = "";
		String url = "";
		
		if(session.getAttribute("memberId") != null) {
			if(session.getAttribute("memberId").equals(params.get("memberId"))) {
				int result = bService.update(params);
				
				if(result == 1) {
					msg = "글이 수정되었습니다.";
					url = contextRoot+"/board/read.do?typeSeq="+params.get("typeSeq")+"&boardSeq="+params.get("boardSeq");
				} else {
					msg = "글수정에 실패했습니다.";
					url = contextRoot+"/board/goUpdatePage.do?typeSeq="+params.get("typeSeq")+"&boardSeq="+params.get("boardSeq");
				}
				
			} else {
				msg = "본인이 작성한 글이 아닙니다.";
				url = contextRoot+"/board/read.do?typeSeq="+params.get("typeSeq")+"&boardSeq="+params.get("boardSeq");
			}
			
			RedirectView rv = new RedirectView(url);
			mv.setView(rv);
			
		} else {
			msg = "로그인 하세요.";
			mv.addObject("nextLocation", "/index.do");
			mv.setViewName("common/error");
		}
		
		mv.addObject("msg", msg);
		
		return mv;
	}
	
	@RequestMapping("/board/gList.do")
	public ModelAndView gList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("gList");
		
		return mv;
	}
	
	@RequestMapping("/board/grList.do")
	@ResponseBody
	public HashMap<String, Object> grList(@RequestParam HashMap<String, String> params){
		logger.debug("/board/grList.do : "+params);
		
		List<Map<String, Object>> allList = bService.selectBoard(params);
		HashMap<String, Object> gList = new HashMap<String, Object>();
		gList.put("rows", allList);
		
		System.out.println("allList : "+allList);
		System.out.println("gList : "+gList);
		
		return gList;
	}
}
