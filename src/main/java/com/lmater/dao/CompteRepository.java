package com.lmater.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmater.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte, String> {

}
