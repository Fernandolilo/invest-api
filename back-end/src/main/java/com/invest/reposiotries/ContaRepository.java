package com.invest.reposiotries;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.invest.entity.Conta;

@Repository 
public interface ContaRepository extends JpaRepository<Conta, UUID> {
	
	@Query("SELECT MAX(c.numero) FROM Conta c")
	Optional<Integer> findMaxConta();


}
