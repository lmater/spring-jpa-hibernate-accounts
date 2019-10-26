package com.lmater.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmater.entities.Compte;
import com.lmater.entities.Operation;
import com.lmater.metier.IBanqueMetier;

@RestController
public class BanqueRestFulController {

	@Autowired
	private IBanqueMetier banqueMetier;

	@RequestMapping("/listOperations")
	public Page<Operation> list(@RequestParam(name = "codeCompte", defaultValue = "") String codeCompte,
			@RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "size", defaultValue = "2") int s) {
		try {
			Page<Operation> pageOperations = banqueMetier.listOperation(codeCompte, p, s);
			return pageOperations;
		} catch (Exception e) {
			return null;
		}

	}
}
