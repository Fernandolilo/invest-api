package com.syp.invest.entity.dto;

import java.math.BigDecimal;

import com.syp.invest.entity.enums.TipoConta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaNewDTO {

	private Integer numero;
	private Integer agencia;
	private BigDecimal saldo;
	private Integer banco;
	private TipoConta tipo;
	private String cpfOuCnpj;
	private String nome;
	
}
