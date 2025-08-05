package com.invest.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaTrasacaoDepSaqDTO {

	private UUID id;
	private BigDecimal saldo;
	private String cpf;
}
