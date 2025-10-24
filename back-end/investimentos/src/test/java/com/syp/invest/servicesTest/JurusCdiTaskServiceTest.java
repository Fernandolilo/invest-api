package com.syp.invest.servicesTest;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.syp.invest.entity.CategoriaInvestimento;
import com.syp.invest.entity.Conta;
import com.syp.invest.entity.Investimento;
import com.syp.invest.entity.dto.response.CDIResponseDTO;
import com.syp.invest.entity.dto.response.DiaUtilResponseDTO;
import com.syp.invest.entity.enums.Indexador;
import com.syp.invest.entity.enums.RiscoInvestimento;
import com.syp.invest.entity.enums.TipoConta;
import com.syp.invest.entity.enums.TipoInvestimento;
import com.syp.invest.entity.enums.TipoRendimento;
import com.syp.invest.entity.enums.VencimentoInvestimento;
import com.syp.invest.repositories.InvestimentoRepository;
import com.syp.invest.service.CdiService;
import com.syp.invest.service.DataService;
import com.syp.invest.service.impl.JurusCdiTaskServiceImpl;



@ExtendWith(MockitoExtension.class)
public class JurusCdiTaskServiceTest {

	@Mock
	private InvestimentoRepository repository;
	@Mock
	private CdiService cdiService;
	@Mock
	private DataService dataService;

	@InjectMocks
	private JurusCdiTaskServiceImpl service;

	
	
	@Test
	void updateCdiTask() {
		DiaUtilResponseDTO diaUtil = new DiaUtilResponseDTO();
		diaUtil.setDiaUtil(true);
			
		CDIResponseDTO response = new CDIResponseDTO() ;
		
		lenient().when(dataService.diaUtil()).thenReturn(diaUtil);
		response.setCdiDiario(new BigDecimal("0.055131"));
		when(cdiService.foundCDI()).thenReturn(response);
		
	
		Conta conta = Conta.builder()
				.agencia(123)
				.banco(237)
				.cpfOuCnpj("12312312311")
				.nome("Fernano")
				.saldo(new BigDecimal("1000.00"))
				.status(true)
				.tipo(TipoConta.CONTA_CORRENTE)
				.build();
		
		CategoriaInvestimento categoriaInvestimento = CategoriaInvestimento.builder()
			    .descricao("CDI 102%")
			    .dataInicio(LocalDate.now())
			    .indexador(Indexador.CDI)
			    .percentualIndexador(new BigDecimal("102")) // <-- aqui
			    .tipoRendimento(TipoRendimento.PREFIXADO)
			    .risco(RiscoInvestimento.RISCO_BAIXO)
			    .tipo(TipoInvestimento.CDB)
			    .resgatavelAntecipadamente(true)
			    .carencia(VencimentoInvestimento.DIAS_180)
			    .build();
		
		Investimento investimento = Investimento.builder()
				.valor(new BigDecimal("1000.00"))
				.indexador(Indexador.CDI)
				.conta(conta)
				.categoria(categoriaInvestimento)				
				.build();
		
		 Page<Investimento> page = new PageImpl<>(Collections.singletonList(investimento));
	        when(repository.findAllByIndexador(Indexador.CDI, PageRequest.of(0, 100))).thenReturn(page);

	        // 🔸 Chama o método agendado manualmente
	        service.sendJurus();
	}

}
