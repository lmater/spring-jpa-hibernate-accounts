package com.lmater.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmater.security.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
