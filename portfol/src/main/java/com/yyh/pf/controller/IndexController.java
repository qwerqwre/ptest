package com.yyh.pf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/home.do")
	public String home() {
		return "home";
	}

	@RequestMapping("/index.do")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/devNotes.do")
	public String devNotes() {
		return "devNotes";
	}
}
