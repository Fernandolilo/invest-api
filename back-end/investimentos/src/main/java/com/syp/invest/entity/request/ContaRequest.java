package com.syp.invest.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.syp.invest.entity.enums.TipoConta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContaRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private Integer numero;
	private Integer agencia;
	private Integer banco;
	private BigDecimal saldo;
	private TipoConta tipo;
	private String cpfOuCnpj;
	private String selfie;
	
}
