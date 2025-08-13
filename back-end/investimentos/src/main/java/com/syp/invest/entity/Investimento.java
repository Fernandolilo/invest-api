package com.syp.invest.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.syp.invest.entity.enums.TipoInvestimento;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Investimento {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private BigDecimal valor;
	private BigDecimal evolucao;
	private LocalDate instante;
	private TipoInvestimento tipo;
	
	@ManyToOne
	@JoinColumn(name = "conta_id", nullable = false)
	private Conta conta;
	
	

}
