package com.syp.invest.entity.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestimentoNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private BigDecimal valor;
	private String cpfOuCnpj;
	//private UUID conta;
	private LocalDate instante;

}
