package com.lmater.metier.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lmater.dao.CompteRepository;
import com.lmater.dao.OperationRepository;
import com.lmater.entities.Compte;
import com.lmater.entities.CompteCourant;
import com.lmater.entities.Operation;
import com.lmater.entities.Retrait;
import com.lmater.entities.Versement;
import com.lmater.metier.IBanqueMetier;

@Component
@Transactional
public class BanqueMetierImpl implements IBanqueMetier {

	@Autowired
	private CompteRepository compteRepository;

	@Autowired
	private OperationRepository operationRepository;

	@Override
	public Compte consulterCompte(String codeCompte) {
		Optional<Compte> cp = compteRepository.findById(codeCompte);
		if (cp == null)
			throw new RuntimeException("Compte introuvable");
		return cp.get();
	}

	@Override
	public void verser(String codeCompte, double montant) {
		Compte cp = consulterCompte(codeCompte);
		Versement v = new Versement(new Date(), montant, cp);
		operationRepository.save(v);
		cp.setSolde(cp.getSolde() + montant);
		compteRepository.save(cp);
	}

	@Override
	public void retirer(String codeCompte, double montant) {
		Compte cp = consulterCompte(codeCompte);
		double facilitiescaisse = 0;
		if (cp instanceof CompteCourant)
			facilitiescaisse = ((CompteCourant) cp).getDecouvert();
		if (cp.getSolde() + facilitiescaisse < montant)
			throw new RuntimeException("Solde Insiffusant");
		Retrait r = new Retrait(new Date(), montant, cp);
		operationRepository.save(r);
		cp.setSolde(cp.getSolde() - montant);
		compteRepository.save(cp);
	}

	@Override
	public void virement(String codeCompte_1, String codeCompt_2, double montant) {
		retirer(codeCompte_1, montant);
		verser(codeCompt_2, montant);
	}

	@Override
	public Page<Operation> listOperation(String codeCompte, int page, int size) {

		return operationRepository.listOperation(codeCompte, new PageRequest(page, size));
	}

}
