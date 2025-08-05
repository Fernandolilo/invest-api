package com.invest.utils;

public class CartaoUtil {

	// Regex para bandeiras
	private static final String VISA_REGEX = "^4[0-9]{12}(?:[0-9]{3})?$";
	private static final String MASTERCARD_REGEX = "^5[1-5][0-9]{14}$|^2[2-7][0-9]{14}$";
	private static final String AMEX_REGEX = "^3[47][0-9]{13}$";

	/**
	 * Verifica se é um cartão Visa
	 */
	public static boolean isVisa(String numero) {
		return numero != null && numero.matches(VISA_REGEX);
	}

	/**
	 * Verifica se é um cartão Mastercard
	 */
	public static boolean isMastercard(String numero) {
		return numero != null && numero.matches(MASTERCARD_REGEX);
	}

	/**
	 * Verifica se é um cartão American Express
	 */
	public static boolean isAmex(String numero) {
		return numero != null && numero.matches(AMEX_REGEX);
	}

	/**
	 * Detecta automaticamente a bandeira
	 */
	public static String detectarBandeira(String numero) {
		if (isVisa(numero))
			return "VISA";
		if (isMastercard(numero))
			return "MASTERCARD";
		if (isAmex(numero))
			return "AMEX";
		return "DESCONHECIDA";
	}
}
