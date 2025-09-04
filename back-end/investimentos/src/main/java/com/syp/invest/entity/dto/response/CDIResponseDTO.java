package com.syp.invest.entity.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private String data;
	private BigDecimal cdiDiario;
	private BigDecimal cdiAnual;

}
