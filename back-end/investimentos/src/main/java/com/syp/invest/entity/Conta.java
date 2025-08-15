package com.syp.invest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.syp.invest.entity.enums.TipoConta;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private Integer numero;
	private Integer agencia;
	private Integer banco;
	
	@Column(name = "saldo_da_conta", nullable = false)
	@DecimalMin(value = "0.0", message = "saldo.")
	private BigDecimal saldo;
	
	@Column(name = "status_da_conta", nullable = false)
	private boolean status;
	
	@Column(name = "tipo_da_conta", nullable = false)
	private TipoConta tipo;
	
	@Column(name = "cpf_ou_cnpj", nullable = false)
	private String cpfOuCnpj;
	
	@Column(name = "titular_da_conta", nullable = false)
	private String nome;
	
	
	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Investimento> investimentos = new ArrayList<>();
	
	
}
