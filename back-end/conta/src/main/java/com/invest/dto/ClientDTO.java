package com.invest.dto;

import java.util.List;

import com.invest.entity.enums.TipoPessoa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
	
	private String nome;
	private String cpfOuCnpj;
	private String celular;
	private String telefone;
	private String email;
	private TipoPessoa tipo;
    private List<ContaDTO> contas; // Lista de contas (múltiplas)

}
