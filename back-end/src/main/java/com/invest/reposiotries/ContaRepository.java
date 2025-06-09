package com.invest.reposiotries;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invest.entity.Conta;

@Repository 
public interface ContaRepository extends JpaRepository<Conta, UUID> {

}
