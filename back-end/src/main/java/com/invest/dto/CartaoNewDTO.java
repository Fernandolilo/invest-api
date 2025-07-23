package com.invest.dto;

import java.io.Serializable;
import java.util.UUID;

import com.invest.entity.enums.BandeiraCartao;
import com.invest.entity.enums.TipoCartao;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartaoNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String nomeTitular;


	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoCartao tipo; // CRÉDITO ou DÉBITO

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)	
	private BandeiraCartao bandeira;
	
	
	@Column(nullable = false)
	private String senha;
	
	private UUID contaId;

}
