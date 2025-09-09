package com.syp.invest.entity.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class FeriadoResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	

    @JsonProperty("diaMes")
    private String diaMes;

    @JsonProperty("diaSemana")
    private String diaSemana;

    @JsonProperty("nomeFeriado")
    private String nomeFeriado;
}
