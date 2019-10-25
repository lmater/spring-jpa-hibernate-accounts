package com.lmater.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmater.security.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
