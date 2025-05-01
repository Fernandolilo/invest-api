package com.wefit.test.entity.dto.requests;

import com.wefit.test.entity.dto.ClientNewDTO;
import com.wefit.test.entity.dto.EnderecoNewDTO;

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
