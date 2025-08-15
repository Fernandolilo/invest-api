package com.syp.invest.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.syp.invest.entity.enums.TipoInvestimento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity

public class Investimento {

	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "valor_investimento", nullable = false)
	@NotNull(message = "O valor do investimento é obrigatório.")
	@DecimalMin(value = "1.0", message = "O valor deve ser maior que zero.")
	private BigDecimal valor;

	@Column(name = "evolucao_investimento")
	private BigDecimal evolucao; // Aqui você pode validar conforme regra de negócio

	@Column(name = "data_do_investimento", nullable = false)
	@NotNull(message = "A data do investimento é obrigatória.")
	@PastOrPresent(message = "A data não pode estar no futuro.")
	private LocalDate instante;

	@Column(name = "tipo_de_investimento", nullable = false)
	@NotNull(message = "O tipo de investimento é obrigatório.")
	private TipoInvestimento tipo;
	@ManyToOne
	@JoinColumn(name = "conta_id", nullable = false)
	private Conta conta;

}
