package com.syp.invest.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.syp.invest.entity.CategoriaInvestimento;
import com.syp.invest.entity.dto.CategoriaInvestimentoDTO;
import com.syp.invest.entity.dto.CategoriaInvestimentoNewDTO;
import com.syp.invest.entity.enums.Indexador;
import com.syp.invest.entity.enums.RiscoInvestimento;
import com.syp.invest.entity.enums.TipoInvestimento;
import com.syp.invest.entity.enums.TipoRendimento;
import com.syp.invest.entity.enums.VencimentoInvestimento;
import com.syp.invest.repositories.CategoriaInvestimentosRepository;
import com.syp.invest.service.impl.CategoriaInvestimentosServiceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaInvestimentosRepository repository;

    @Mock
    private ModelMapper mapper;
    
    @InjectMocks
    private CategoriaInvestimentosServiceImpl service;
    
    @BeforeEach
    void setUp() {
        mapper = new ModelMapper(); // inicializa corretamente
        service = new CategoriaInvestimentosServiceImpl(repository, mapper);
    }

    @Test
    @DisplayName("Deve Salvar uma nova carteira de investimento")
    void saveCategoriaInvestimento() {
        // given
        CategoriaInvestimentoNewDTO dto = categoriaNewInvestimento();
        dto.setDataVencimento(calcularDataVencimento(VencimentoInvestimento.QUALQUER_INSTANTE, LocalDate.now()));

        when(repository.save(any(CategoriaInvestimento.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        CategoriaInvestimentoDTO result = service.save(dto);

        // then - verificações
        assertNotNull(result); // o objeto retornado não pode ser nulo
        assertNotNull(result.getDescricao());
        assertNotNull(result.getDataInicio());
     

        assertEquals("Investimento CDI", result.getDescricao());
        assertEquals(VencimentoInvestimento.QUALQUER_INSTANTE, result.getCarencia());
        assertEquals(Indexador.CDI, result.getIndexador());
        assertEquals(BigDecimal.valueOf(102), result.getPercentualIndexador());
        assertEquals(RiscoInvestimento.RISCO_BAIXO, result.getRisco());
        assertEquals(TipoInvestimento.CDB, result.getTipo());
        assertEquals(TipoRendimento.PREFIXADO, result.getTipoRendimento());

        // valida que chamou o repository
        verify(repository, times(1)).save(any(CategoriaInvestimento.class));
    }

	private CategoriaInvestimentoNewDTO categoriaNewInvestimento() {
		CategoriaInvestimentoNewDTO dto = CategoriaInvestimentoNewDTO.builder()
                .descricao("Investimento CDI")
                .indexador(Indexador.CDI)
                .percentualAdicional(BigDecimal.ZERO)
                .percentualIndexador(BigDecimal.valueOf(102))
                .risco(RiscoInvestimento.RISCO_BAIXO)
                .carencia(VencimentoInvestimento.QUALQUER_INSTANTE)
                .tipo(TipoInvestimento.CDB)
                .resgatavelAntecipadamente(true)
                .tipoRendimento(TipoRendimento.PREFIXADO)
                .dataVencimento(LocalDate.now().plusYears(1))
                .build();
		return dto;
	}

    
    
    private LocalDate calcularDataVencimento(VencimentoInvestimento carencia, LocalDate dataManual) {
	    LocalDate hoje = LocalDate.now();

	    switch (carencia) {
	        case QUALQUER_INSTANTE:
	            return hoje;
	        case DIAS_30:
	            return hoje.plusDays(30);
	        case DIAS_60:
	            return hoje.plusDays(60);
	        case DIAS_90:
	            return hoje.plusDays(90);
	        case DIAS_180:
	            return hoje.plusDays(180);
	        case ANUAL:
	            return hoje.plusYears(1);
	        case VENCIMENTO:
	            if (dataManual == null) {
	                throw new IllegalArgumentException("A data de vencimento manual deve ser informada quando a carência for VENCIMENTO.");
	            }
	            return dataManual;
	        default:
	            throw new IllegalArgumentException("Carência não suportada: " + carencia);
	    }
	}

}
