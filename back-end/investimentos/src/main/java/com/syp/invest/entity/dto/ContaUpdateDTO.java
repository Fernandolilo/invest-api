package com.syp.invest.entity.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.syp.invest.entity.enums.TipoConta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContaUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private UUID id;
	private Integer numero;
	private Integer agencia;
	private BigDecimal saldo;
	private Integer banco;
	private TipoConta tipo;
	private String cpf;
}
