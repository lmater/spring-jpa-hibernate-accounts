package com.lmater.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lmater.metier.IBanqueMetier;

@Controller
public class BanqueController {

	@Autowired
	private IBanqueMetier banqueMetier;

	@RequestMapping("/operations")
	public String index() {
		return "comptes";
	}
}
