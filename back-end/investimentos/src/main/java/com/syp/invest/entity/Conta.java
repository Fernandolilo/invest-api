package com.syp.invest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.syp.invest.entity.enums.TipoConta;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Conta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotNull(message = "O número da conta é obrigatório.")
	@Column(nullable = false)
	private Integer numero;

	@NotNull(message = "A agência é obrigatória.")
	@Column(nullable = false)
	private Integer agencia;

	@NotNull(message = "O banco é obrigatório.")
	@Column(nullable = false)
	private Integer banco;

	@NotNull(message = "O saldo da conta é obrigatório.")
	@DecimalMin(value = "0.0", inclusive = true, message = "O saldo deve ser maior ou igual a zero.")
	@Column(name = "saldo_da_conta", nullable = false, precision = 15, scale = 2)
	private BigDecimal saldo;

	@Column(name = "status_da_conta", nullable = false)
	private boolean status;

	@NotNull(message = "O tipo da conta é obrigatório.")
	@Column(name = "tipo_da_conta", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoConta tipo;

	@NotNull(message = "O CPF ou CNPJ é obrigatório.")
	@Size(min = 11, max = 14, message = "CPF ou CNPJ inválido.")
	@Column(name = "cpf_ou_cnpj", nullable = false)
	private String cpfOuCnpj;

	@NotNull(message = "O nome do titular é obrigatório.")
	@Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
	@Column(name = "titular_da_conta", nullable = false)
	private String nome;

	@JsonBackReference
	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Investimento> investimentos = new ArrayList<>();
	
	
}
