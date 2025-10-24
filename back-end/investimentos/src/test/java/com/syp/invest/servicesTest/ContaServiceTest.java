package com.syp.invest.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.syp.invest.entity.Conta;
import com.syp.invest.entity.dto.ContaDTO;
import com.syp.invest.repositories.ContaRepository;
import com.syp.invest.service.impl.ContaServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private ContaRepository repository;

	@InjectMocks
	private ContaServiceImpl service;

	@Test
	void foundConta() {
		UUID id = UUID.randomUUID();
		Conta conta = new Conta();
		conta.setId(id);

		ContaDTO contaDTO = new ContaDTO();
		contaDTO.setId(id);

		when(repository.findById(id)).thenReturn(Optional.of(conta));
		when(mapper.map(conta, ContaDTO.class)).thenReturn(contaDTO);

		ContaDTO result = service.findConta(id);
		assertNotNull(result);
		assertEquals(id, result.getId());
	}
}
