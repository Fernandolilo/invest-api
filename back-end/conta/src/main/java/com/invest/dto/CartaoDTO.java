package com.invest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.invest.entity.enums.BandeiraCartao;
import com.invest.entity.enums.TipoCartao;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class CartaoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private String numero;

	@Column(nullable = false)
	private String nomeTitular;

	@Column(nullable = false)
	private LocalDate validade;

	@Column(nullable = false)
	private String cvv;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoCartao tipo; // CRÉDITO ou DÉBITO

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)	
	private BandeiraCartao bandeira;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal limite; // usado apenas para cartões de crédito

	
	

}
