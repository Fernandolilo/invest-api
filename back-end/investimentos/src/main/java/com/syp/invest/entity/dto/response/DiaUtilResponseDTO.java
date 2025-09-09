package com.syp.invest.entity.dto.response;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DiaUtilResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private LocalDate dia; // Data consultada
	private boolean diaUtil;
	private String name;
	
}
