package com.syp.invest.entity.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.syp.invest.entity.Investimento;
import com.syp.invest.entity.CategoriaInvestimento;
import com.syp.invest.entity.enums.Indexador;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvestimentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal valor;
    private BigDecimal evolucao;
    private LocalDate instante;
    private Indexador indexador;
    private CategoriaInvestimento categoria;

    // 🔑 Construtor manual que recebe a entidade
    public InvestimentoDTO(Investimento entity) {
        this.valor = entity.getValor();
        this.evolucao = entity.getEvolucao();
        this.instante = entity.getInstante();
        this.indexador = entity.getIndexador();
        this.categoria = entity.getCategoria();
    }
}
