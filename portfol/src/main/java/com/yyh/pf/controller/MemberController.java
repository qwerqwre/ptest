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

import com.yyh.pf.dto.Member;
import com.yyh.pf.exception.MemberNotFoundException;
import com.yyh.pf.exception.PasswordMissMatchException;
import com.yyh.pf.service.MemberService;

@Controller
public class MemberController {
	// Service DI 구현 코드
		@Autowired private MemberService mService;
		Logger logger = Logger.getLogger(MemberController.class);
		
		@Value("#{config['project.context.path']}")
		private String contextRoot;

		/**
		 * @param memberInfo
		 * @return ModelAndView
		 */
		@RequestMapping("/member/join.do")
		public ModelAndView join(@RequestParam HashMap<String, Object> memberInfo) {
			logger.debug("/member/join.do : "+memberInfo);
			
			String msg = (mService.join(memberInfo) == 1) ? "회원가입 성공!!" : "회원가입 실패!!";

			ModelAndView mv = new ModelAndView();
			RedirectView rv = new RedirectView(contextRoot+"/member/login.do");
			mv.setView(rv);
			mv.addObject("msg", msg);

			return mv;
		}

		/**
		 * 로그인 성공 -> index 페이지로
		 * 로그인 실패 (비밀번호 불일치)
		 * 로그인 실패 (아이디 없음)
		 * @param userId
		 * @param userPw
		 * @return ModelAndView
		 */
		@RequestMapping("/member/login.do")
		public ModelAndView login(String memberId, String memberPw, HttpSession session) {
			ModelAndView mv = new ModelAndView();
			String page = "login";
			String msg = "시스템 에러";

			// 에러 나는 이유 : 예외가 돌아올 수도 있다면 try-catch를 해주어야 함
			Member member = null;
			try {
				member = mService.login(memberId, memberPw);
				
				session.setAttribute("typeSeq", member.getTypeSeq());
				session.setAttribute("memberIdx", member.getMemberIdx());
				session.setAttribute("memberId", member.getMemberId());
				session.setAttribute("memberNick", member.getMemberNick());
				
				System.out.println("memberIdx : "+session.getAttribute("memberIdx"));
				System.out.println("typeSeq : "+session.getAttribute("typeSeq"));
				
				// 로그인이 성공하면 index 페이지로
				RedirectView rv = new RedirectView(contextRoot+"/index.do");
				mv.setView(rv); // 요청을 다시해서 setView
				return mv;

			} catch (PasswordMissMatchException pme) { // 비밀번호가 불일치 할 때
				msg = pme.getMessage();

			} catch (MemberNotFoundException mnfe) { // 아이디가 없을 때
				msg = mnfe.getMessage();

			} catch (Exception e) {
				e.printStackTrace();
			}

			mv.setViewName(page);
			mv.addObject("msg", msg);

			return mv;
		}

		@RequestMapping("/member/goLoginPage.do")
		public ModelAndView goLoginPage(HttpSession session) {
			ModelAndView mv = new ModelAndView();
			
			if(session.getAttribute("memberId") != null) {
				RedirectView rv = new RedirectView(contextRoot+"/index.do");
				mv.setView(rv);
			} else {
				mv.setViewName("login");
			}
			
			return mv;
		}
		
		@RequestMapping("/member/logout.do")
		public ModelAndView logout(HttpSession session) {
			session.invalidate();
			ModelAndView mv = new ModelAndView();
			RedirectView rv = new RedirectView(contextRoot+"/index.do");
			mv.setView(rv);
					
			return mv;
		}
		
		@RequestMapping("/member/checkId.do")
		@ResponseBody
		public int checkId(@RequestParam HashMap<String, String> params) {
			int cnt = mService.checkId(params);
			logger.debug("/member/checkId.do params: "+params);
			logger.debug("Result:" + cnt);
			
			return cnt;
		}
		
		/**
		 * 회원 관리
		 * @return
		 */
		@RequestMapping("/member/mList.do")
		public ModelAndView mList() {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("mList");
			
			return mv;
		}
		
		@RequestMapping("/member/list.do")
		@ResponseBody
		public HashMap<String, Object> list(@RequestParam HashMap<String, String> params) {
			logger.debug("/member/list.do params : "+params);
						
			// 회원 리스트 불러오기
			List<Map<String, Object>> mList = mService.selectMember(params);			
			HashMap<String, Object> memberList = new HashMap<String, Object>();
			memberList.put("rows", mList);
			
			return memberList;
		}
		
		@RequestMapping("/member/delMember.do")
		@ResponseBody
		public HashMap<String, String> delMember(@RequestParam HashMap<String, String> params) {
			logger.debug("/member/delMember.do params : "+params);
			HashMap<String, String> map = new HashMap<String, String>();
			int cnt = mService.delMember(params);
			map.put("msg", (cnt ==1) ? "삭제 되었습니다." : "삭제 실패!");
			map.put("result", String.valueOf(cnt));
			
			return map;
		}
}
