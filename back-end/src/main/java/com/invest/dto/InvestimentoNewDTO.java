package com.invest.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestimentoNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private double valor;
	private String cpfOuCnpj;
	private UUID conta;

}
