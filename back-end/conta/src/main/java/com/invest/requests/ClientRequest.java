package com.invest.requests;

import com.invest.dto.ClientNewDTO;
import com.invest.dto.EnderecoNewDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {
	@NotNull
    @Valid
    private ClientNewDTO client;

    @NotNull
    @Valid
    private EnderecoNewDTO endereco;
}
