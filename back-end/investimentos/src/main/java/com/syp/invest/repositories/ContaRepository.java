package com.syp.invest.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syp.invest.entity.Conta;
@Repository
public interface ContaRepository extends JpaRepository<Conta, UUID> {

}
