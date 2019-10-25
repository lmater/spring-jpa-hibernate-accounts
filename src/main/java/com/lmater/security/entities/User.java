package com.lmater.security.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	private String login;
	private String pass;
	private boolean active;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Collection<Users_Roles> users_roles;

	public User() {
		super();
	}

	public User(String login, String pass, boolean active) {
		super();
		this.login = login;
		this.pass = pass;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Collection<Users_Roles> getUsers_roles() {
		return users_roles;
	}

	public void setUsers_roles(Collection<Users_Roles> users_roles) {
		this.users_roles = users_roles;
	}

}
