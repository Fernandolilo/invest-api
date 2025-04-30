package com.wefit.test.entity.dto.response;

import com.wefit.test.entity.dto.ClientNewDTO;
import com.wefit.test.entity.dto.EnderecoNewDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientNewDtoResponse {
	@NotNull
    @Valid
    private ClientNewDTO client;

    @NotNull
    @Valid
    private EnderecoNewDTO endereco;
}
