package com.lmater.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("R")
public class Retrait extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1907198867085464842L;
	public Retrait() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Retrait(Date dateOperation, double montant, Compte compte) {
		super(dateOperation, montant, compte);
		// TODO Auto-generated constructor stub
	}

}
