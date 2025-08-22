package com.syp.invest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.syp.invest.entity.enums.RiscoInvestimento;
import com.syp.invest.entity.enums.TipoInvestimento;
import com.syp.invest.entity.enums.TipoRendimento;
import com.syp.invest.entity.enums.VencimentoInvestimento;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "categoria")
public class CategoriaInvestimento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@EqualsAndHashCode.Include
	private UUID id;

	@NotNull(message = "A descrição é obrigatória.")
	@Size(min = 3, max = 100, message = "A descrição deve ter entre 3 e 100 caracteres.")
	@Column(nullable = false)
	private String descricao;
	
	 // percentual adicional sobre o índice, ex: IPCA + 5%
    @NotNull(message = "O percentual adicional é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = true, message = "O percentual adicional deve ser maior ou igual a zero.")
    @Column(name = "percentual_adicional", precision = 5, scale = 2, nullable = false)
    private BigDecimal percentualAdicional;

	@NotNull(message = "O percentual do indexador é obrigatório.")
	@DecimalMin(value = "0.0", inclusive = false, message = "O percentual deve ser maior que zero.")
	@Column(name = "percentual_indexador", precision = 5, scale = 2, nullable = false)
	private BigDecimal percentualIndexador;

	@NotNull(message = "A carência é obrigatória.")
	@Enumerated(EnumType.STRING)
	@Column(name = "carencia", nullable = false)
	private VencimentoInvestimento carencia;

	@Column(name = "data_inicial")
	//@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataInicio;

	@Column(name = "vencimento")
//	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataVencimento;

	@NotNull(message = "O tipo de investimento é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_investimento", nullable = false)
	private TipoInvestimento tipo;

	@NotNull(message = "O tipo de rendimento é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_rendimento", nullable = false)
	private TipoRendimento tipoRendimento;

	@NotNull(message = "O risco do investimento é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(name = "risco_investimento", nullable = false)
	private RiscoInvestimento risco;

	@NotNull(message = "Informe se é resgatável antecipadamente.")
	@Column(name = "antecipacao", nullable = false)
	private Boolean resgatavelAntecipadamente;

	@JsonBackReference
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Investimento> investimentos = new ArrayList<>();

   }
