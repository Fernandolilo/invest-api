package com.syp.invest.entity.enums;

public enum TipoInvestimento {

    CDB("Renda Fixa", "Baixo"),
    LCI("Renda Fixa", "Baixo"),
    LCA("Renda Fixa", "Baixo"),
    DEBENTURE("Renda Fixa", "Médio"),
    TESOURO_DIRETO("Renda Fixa", "Baixo"),
    ACOES("Renda Variável", "Alto"),
    ETF("Renda Variável", "Alto"),
    FUNDOS_IMOBILIARIOS("Renda Variável", "Médio"),
    CRIPTOMOEDA("Alternativo", "Alto");

    private final String categoria;
    private final String risco;

    TipoInvestimento(String categoria, String risco) {
        this.categoria = categoria;
        this.risco = risco;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getRisco() {
        return risco;
    }
}
