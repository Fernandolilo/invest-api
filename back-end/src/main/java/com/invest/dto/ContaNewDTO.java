package com.invest.dto;

import com.invest.entity.enums.TipoConta;

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
	private double saldo;
	private Integer banco;
	private TipoConta tipo;
	private String cpf;
}
