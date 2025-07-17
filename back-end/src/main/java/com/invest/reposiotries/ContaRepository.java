package com.invest.reposiotries;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.invest.entity.Client;
import com.invest.entity.Conta;

@Repository 
public interface ContaRepository extends JpaRepository<Conta, UUID> {
	
	@Query("SELECT MAX(c.numero) FROM Conta c")
	Optional<Integer> findMaxConta();
	
	@Query("SELECT c.client FROM Conta c WHERE c.client.cpfOuCnpj = :cpfouCnpj")
	Optional<Client> findClienteByContaCpfouCnpj(@Param("cpfouCnpj") String cpfouCnpj);

	Optional<Conta> findByClientId(UUID clientId);

	List<Conta> findAllByClientId(UUID clientId);

	

}
