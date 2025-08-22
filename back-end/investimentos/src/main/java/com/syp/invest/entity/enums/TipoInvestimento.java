package com.syp.invest.entity.enums;

public enum TipoInvestimento {
    CDB("Renda Fixa", "Baixo"),
    LCI("Renda Fixa", "Baixo"),
    LCA("Renda Fixa", "Baixo"),
    DEBENTURE("Renda Fixa", "Médio");

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
