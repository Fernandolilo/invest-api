package com.syp.invest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.syp.invest.entity.enums.Indexador;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Investimento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "valor_investimento", nullable = false)
    @NotNull(message = "O valor do investimento é obrigatório.")
    @DecimalMin(value = "1.0", message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    @Column(name = "evolucao_do_investimento")
    private BigDecimal evolucao;

    @Column(name = "data_do_investimento", nullable = false)
    @NotNull(message = "A data do investimento é obrigatória.")
    @PastOrPresent(message = "A data não pode estar no futuro.")
    private LocalDate instante;
    
    @Column(name = "indexador")
    @Enumerated(EnumType.STRING)
    private Indexador indexador;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaInvestimento categoria;
    
    @JsonBackReference
    @OneToMany(mappedBy = "investimento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Aporte> depositos = new ArrayList<>();
}
