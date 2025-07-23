package com.invest.utils;

import com.invest.entity.enums.BandeiraCartao;

public class TipoBandeiraCartaoUtil {

    private static final BandeiraCartao DEFAULT_VALUE_IF_NULL = BandeiraCartao.VISA;
    private static final BandeiraCartao DEFAULT_VALUE_IF_INVALID = BandeiraCartao.MASTERCARD;

    public static BandeiraCartao fromString(String tipoStr) {
        if (tipoStr == null) {
            return DEFAULT_VALUE_IF_NULL;
        }
        try {
            return BandeiraCartao.valueOf(tipoStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return DEFAULT_VALUE_IF_INVALID;
        }
    }
}
