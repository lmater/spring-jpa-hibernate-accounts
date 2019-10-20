package com.lmater.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmater.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
