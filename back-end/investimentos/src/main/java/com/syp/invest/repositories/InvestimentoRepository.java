package com.syp.invest.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syp.invest.entity.Investimento;
import com.syp.invest.entity.enums.Indexador;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, UUID> {

	 Page<Investimento> findAllByIndexador(Indexador indexador, Pageable pageable);
	 
	 List<Investimento> findByContaId(UUID id);
}
