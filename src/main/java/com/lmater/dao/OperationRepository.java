package com.lmater.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmater.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long>{

}
