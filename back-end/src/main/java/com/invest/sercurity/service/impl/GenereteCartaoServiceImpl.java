package com.invest.sercurity.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.invest.entity.enums.BandeiraCartao;
import com.invest.service.GenereteCartaoService;

@Service
public class GenereteCartaoServiceImpl implements GenereteCartaoService {

	private static final Random random = new Random();

	@Override
	public String gerarNumeroCartao(BandeiraCartao bandeira) {
		String prefixo = obterPrefixoPorBandeira(bandeira);
		int totalDigitos = 16;

		// Gera os primeiros dígitos (sem o dígito verificador final)
		StringBuilder numero = new StringBuilder(prefixo);
		while (numero.length() < totalDigitos - 1) {
			numero.append(random.nextInt(10));
		}

		// Adiciona o dígito verificador de Luhn
		int digitoVerificador = calcularDigitoLuhn(numero.toString());
		numero.append(digitoVerificador);

		return numero.toString();
	}

	private String obterPrefixoPorBandeira(BandeiraCartao bandeira) {
		return switch (bandeira) {
		case VISA -> "4";
		case MASTERCARD -> String.valueOf(51 + random.nextInt(5)); // 51 a 55
		case ELO -> "636297"; // Exemplo de prefixo real do ELO
		// case AMERICAN_EXPRESS -> "34"; // ou 37
		// case HIPERCARD -> "3841"; // exemplo
		default -> "9"; // prefixo genérico
		};
	}

	private int calcularDigitoLuhn(String numeroParcial) {
		int soma = 0;
		boolean alternar = true;

		for (int i = numeroParcial.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(numeroParcial.substring(i, i + 1));
			if (alternar) {
				n *= 2;
				if (n > 9)
					n -= 9;
			}
			soma += n;
			alternar = !alternar;
		}

		int digitoVerificador = (10 - (soma % 10)) % 10;
		return digitoVerificador;

	}

}
