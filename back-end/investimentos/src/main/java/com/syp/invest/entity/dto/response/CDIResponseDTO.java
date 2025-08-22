package com.syp.invest.entity.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CDIResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String data;
	private BigDecimal cdiDiario;
	private BigDecimal cdiAnual;

}
