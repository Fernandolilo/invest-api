package com.invest.utils;

import com.invest.entity.enums.TipoCartao;

public class TipoCartaoUtil {

    public static TipoCartao fromString(String tipoStr) {
        if (tipoStr == null) {
            return TipoCartao.DEBITO; // padrão caso seja null
        }
        try {
            return TipoCartao.valueOf(tipoStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            // Caso o valor não seja DEBITO, CREDITO ou MULTIPLO
            return TipoCartao.DEBITO; // ou lance exceção se preferir
        }
    }
}

