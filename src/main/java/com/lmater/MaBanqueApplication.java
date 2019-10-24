package com.lmater;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.lmater.dao.ClientRepository;
import com.lmater.dao.CompteRepository;
import com.lmater.dao.OperationRepository;
import com.lmater.entities.Client;
import com.lmater.entities.Compte;
import com.lmater.entities.CompteCourant;
import com.lmater.entities.CompteEpargne;
import com.lmater.entities.Operation;
import com.lmater.entities.Retrait;
import com.lmater.entities.Versement;
import com.lmater.metier.impl.BanqueMetierImpl;

@SpringBootApplication
public class MaBanqueApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private CompteRepository compteRepository;

	@Autowired
	private OperationRepository operationRepository;

	@Autowired
	private BanqueMetierImpl banqueMetierImpl;

	public static void main(String[] args) {
		SpringApplication.run(MaBanqueApplication.class, args);
//		ApplicationContext ctx = SpringApplication.run(MaBanqueApplication.class, args);
//		ClientRepository clientRepository = ctx.getBean(ClientRepository.class);
//		clientRepository.save(new Client("lmater", "@gmail"));
	}

	@Override
	public void run(String... args) throws Exception {
		Client c1 = clientRepository.save(new Client("lmater  1", "@gmail 1"));
		Client c2 = clientRepository.save(new Client("lmater  2", "@gmail 2"));

		Compte cp1 = compteRepository.save(new CompteCourant("c1", new Date(), 9000, c1, 6000));
		Compte cp2 = compteRepository.save(new CompteEpargne("c2", new Date(), 4000, c2, 5.5));

		operationRepository.save(new Versement(new Date(), 550, cp1));
		operationRepository.save(new Versement(new Date(), 150, cp1));
		operationRepository.save(new Retrait(new Date(), 350, cp1));
		operationRepository.save(new Versement(new Date(), 1050, cp1));

		operationRepository.save(new Versement(new Date(), 5550, cp2));
		operationRepository.save(new Versement(new Date(), 1520, cp2));
		operationRepository.save(new Retrait(new Date(), 3500, cp2));
		operationRepository.save(new Versement(new Date(), 150, cp2));

		System.out.println("consulterCompte: "+banqueMetierImpl.consulterCompte(cp1.getCodeCompte()).getClient().getName());

		banqueMetierImpl.verser(cp1.getCodeCompte(), 11111);
		banqueMetierImpl.retirer(cp1.getCodeCompte(), 111);
		banqueMetierImpl.virement(cp1.getCodeCompte(), cp2.getCodeCompte(), 11111);
	}

}
