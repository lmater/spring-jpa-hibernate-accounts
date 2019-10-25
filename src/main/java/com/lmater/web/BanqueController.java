package com.lmater.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmater.entities.Compte;
import com.lmater.entities.Operation;
import com.lmater.metier.IBanqueMetier;

@Controller
public class BanqueController {

	@Autowired
	private IBanqueMetier banqueMetier;

	@RequestMapping("/operations")
	public String index() {
		return "comptes";
	}

	@RequestMapping("/consulterCompte")
	public String consulter(Model model, @RequestParam(name = "codeCompte", defaultValue = "") String codeCompte,
			@RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "size", defaultValue = "2") int s) {
		model.addAttribute("codeCompte", codeCompte);
		try {
			Compte cp = banqueMetier.consulterCompte(codeCompte);
			Page<Operation> pageOperations = banqueMetier.listOperation(codeCompte, p, s);
			model.addAttribute("compte", cp);
			model.addAttribute("listOperations", pageOperations.getContent());

			int[] pages = new int[pageOperations.getTotalPages()];
			model.addAttribute("pages", pages);
			model.addAttribute("size", s);
			model.addAttribute("pageCourante", p);

		} catch (Exception e) {
			model.addAttribute("exception", e);
		}
		return "comptes";
	}

	@RequestMapping(value = "/saveOperation", method = RequestMethod.POST)
	public String saveOperation(Model model, String typeOperation, String codeCompte, String codeCompte2,
			String montant) {
		try {
			double montantDouble = Double.valueOf(montant);
			if (typeOperation.equals("VERS")) {
				banqueMetier.verser(codeCompte, montantDouble);
			} else if (typeOperation.equals("VERM")) {
				if (codeCompte2 != null && !codeCompte2.isEmpty()) {
					banqueMetier.virement(codeCompte, codeCompte2, montantDouble);
				} else {
					throw new RuntimeException("Compte distination est vide");
				}
			} else if (typeOperation.equals("RETR")) {
				banqueMetier.retirer(codeCompte, montantDouble);
			}
		} catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consulterCompte?codeCompte=" + codeCompte + "&error=" + e.getMessage();
		}
		return "redirect:/consulterCompte?codeCompte=" + codeCompte;
	}
}
