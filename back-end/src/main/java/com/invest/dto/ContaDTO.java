package com.invest.dto;

import java.util.UUID;

import com.invest.entity.enums.TipoConta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO {
	
	private UUID id;
	private Integer numero;
	private Integer agencia;
	private Integer banco;
	private double saldo;
	private TipoConta tipo;
	private String nome;
	private String cpfOuCnpj;
	private String selfie;

}
