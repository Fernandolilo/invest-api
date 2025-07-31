package com.invest.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.invest.entity.enums.TipoConta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaUpdateDTO {

	private UUID id;
	private Integer numero;
	private Integer agencia;
	private BigDecimal saldo;
	private Integer banco;
	private TipoConta tipo;
	private String cpf;
}
