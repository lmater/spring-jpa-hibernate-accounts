package com.lmater.metier;

import org.springframework.data.domain.Page;

import com.lmater.entities.Compte;
import com.lmater.entities.Operation;

public interface IBanqueMetier {

	public Compte consulterCompte(String codeCompte);

	public void verser(String codeCompte, double montant);

	public void retirer(String codeCompte, double montant);

	public void virement(String codeCompte_1, String codeCompt_2, double montant);

	public Page<Operation> listOperation(String codeCompte, int page, int size);

}
