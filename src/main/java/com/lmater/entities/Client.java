package com.lmater.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Client implements Serializable {

	@Id
	@GeneratedValue
	private Long code;
	private String name;
	private String email;

	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
	private Collection<Compte> compte;

	public Client() {
		super();
	}

	public Client(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonIgnore

	public Collection<Compte> getCompte() {
		return compte;
	}

	public void setCompte(Collection<Compte> compte) {
		this.compte = compte;
	}

}
