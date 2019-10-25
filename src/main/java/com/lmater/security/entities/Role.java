package com.lmater.security.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.lmater.entities.Client;

@Entity
public class Role implements Serializable {

	@Id
	@GeneratedValue
	private Long id;
	private String role;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private Collection<Users_Roles> users_roles;

	public Role() {
		super();
	}

	public Role(String role) {
		super();
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Collection<Users_Roles> getUsers_roles() {
		return users_roles;
	}

	public void setUsers_roles(Collection<Users_Roles> users_roles) {
		this.users_roles = users_roles;
	}

}
