package com.invest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.invest.entity.enums.BandeiraCartao;
import com.invest.entity.enums.TipoCartao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Cartao implements Serializable {  // Corrigido nome da classe

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String numero;

	@Column(nullable = false)
	private String nomeTitular;

	@Column(nullable = false)
	private LocalDate validade;

	@Column(nullable = false)
	private String cvv;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoCartao tipo; // CRÉDITO ou DÉBITO

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)	
	private BandeiraCartao bandeira;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal limite; // usado apenas para cartões de crédito

	@Column(nullable = false)
	private String senha;

	// 🎯 Relacionamento com Conta (muitos cartões para uma conta)
	@ManyToOne(optional = false)
	@JoinColumn(name = "conta_id", nullable = false)
	private Conta conta;
}
