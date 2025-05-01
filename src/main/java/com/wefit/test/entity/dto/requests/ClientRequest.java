package com.wefit.test.entity.dto.requests;

import com.wefit.test.entity.dto.ClientNewDTO;
import com.wefit.test.entity.dto.EnderecoNewDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientRequest {
	@NotNull
    @Valid
    private ClientNewDTO client;

    @NotNull
    @Valid
    private EnderecoNewDTO endereco;
}
