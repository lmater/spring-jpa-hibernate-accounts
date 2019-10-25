package com.lmater.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

	@RequestMapping(value = "/")
	public String home() {
		return "redirect:/operations";
	}


	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/403")
	public String accessDenied() {
		return "403";
	}

}
