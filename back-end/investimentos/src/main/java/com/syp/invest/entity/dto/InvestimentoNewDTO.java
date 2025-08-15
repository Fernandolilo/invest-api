package com.syp.invest.entity.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.syp.invest.entity.enums.TipoInvestimento;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestimentoNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private UUID contaId;
	private BigDecimal valor;
	private String cpfOuCnpj;
	private TipoInvestimento tipo;

}
