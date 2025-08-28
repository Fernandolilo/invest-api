package com.syp.invest.entity.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IpcaResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private LocalDate data;
	private BigDecimal mensal;
	private BigDecimal acumuladoAno;
	private BigDecimal acumulado12Meses;
}
