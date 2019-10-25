package com.lmater;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.DigestUtils;

import com.lmater.dao.ClientRepository;
import com.lmater.dao.CompteRepository;
import com.lmater.dao.OperationRepository;
import com.lmater.entities.Client;
import com.lmater.entities.Compte;
import com.lmater.entities.CompteCourant;
import com.lmater.entities.CompteEpargne;
import com.lmater.entities.Retrait;
import com.lmater.entities.Versement;
import com.lmater.metier.impl.BanqueMetierImpl;
import com.lmater.security.dao.RoleRepository;
import com.lmater.security.dao.UserRepository;
import com.lmater.security.dao.Users_RolesRepository;
import com.lmater.security.entities.Role;
import com.lmater.security.entities.User;
import com.lmater.security.entities.Users_Roles;

@SpringBootApplication
public class MaBanqueApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private Users_RolesRepository users_RolesRepository;

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
		String plaintext = "1234";
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(plaintext.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1,digest);
		String hashtext = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		while(hashtext.length() < 32 ){
		  hashtext = "0"+hashtext;
		}
		User u1 = userRepository.save(new User("super", hashtext, true));
		User u2 = userRepository.save(new User("admin", hashtext, true));
		User u3 = userRepository.save(new User("user", hashtext, true));
		Role r1 = roleRepository.save(new Role("SUPERADMIN"));
		Role r2 = roleRepository.save(new Role("ADMIN"));
		Role r3 = roleRepository.save(new Role("USER"));
		users_RolesRepository.save(new Users_Roles(u1, r1));
		users_RolesRepository.save(new Users_Roles(u1, r2));
		users_RolesRepository.save(new Users_Roles(u1, r3));
		users_RolesRepository.save(new Users_Roles(u2, r2));
		users_RolesRepository.save(new Users_Roles(u2, r3));
		users_RolesRepository.save(new Users_Roles(u3, r3));
		
		
		
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

		System.out.println(
				"consulterCompte: " + banqueMetierImpl.consulterCompte(cp1.getCodeCompte()).getClient().getName());

		banqueMetierImpl.verser(cp1.getCodeCompte(), 11111);
		banqueMetierImpl.retirer(cp1.getCodeCompte(), 111);
		banqueMetierImpl.virement(cp1.getCodeCompte(), cp2.getCodeCompte(), 11111);
	}

}
