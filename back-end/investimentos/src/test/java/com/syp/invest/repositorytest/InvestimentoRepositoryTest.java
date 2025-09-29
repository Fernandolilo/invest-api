package com.syp.invest.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.syp.invest.entity.CategoriaInvestimento;
import com.syp.invest.entity.Conta;
import com.syp.invest.entity.Investimento;
import com.syp.invest.entity.dto.CategoriaInvestimentoNewDTO;
import com.syp.invest.entity.dto.ContaNewDTO;
import com.syp.invest.entity.dto.InvestimentoNewDTO;
import com.syp.invest.entity.enums.Indexador;
import com.syp.invest.entity.enums.RiscoInvestimento;
import com.syp.invest.entity.enums.TipoConta;
import com.syp.invest.entity.enums.TipoInvestimento;
import com.syp.invest.entity.enums.TipoRendimento;
import com.syp.invest.entity.enums.VencimentoInvestimento;
import com.syp.invest.repositories.InvestimentoRepository;

@DataJpaTest
class InvestimentoRepositoryTest {

    @Autowired
    private InvestimentoRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private CategoriaInvestimento categoria;
    private Conta conta;
    private Investimento investimento;

    @BeforeEach
    
    void setUp() {    	
    	
    	//persistindo uma categoria
        CategoriaInvestimentoNewDTO categoriaDTO = categoriaNewDTO();        
        categoria = CategoriaInvestimento.builder()
                .indexador(categoriaDTO.getIndexador())
                .descricao(categoriaDTO.getDescricao())
                .carencia(categoriaDTO.getCarencia())
                .tipo(categoriaDTO.getTipo())
                .risco(categoriaDTO.getRisco())
                .percentualIndexador(categoriaDTO.getPercentualIndexador())
                .percentualAdicional(categoriaDTO.getPercentualAdicional())
                .tipoRendimento(categoriaDTO.getTipoRendimento())
                .resgatavelAntecipadamente(categoriaDTO.getResgatavelAntecipadamente())
                .dataInicio(categoriaDTO.getDataInicio())
                .dataVencimento(calcularDataVencimento(VencimentoInvestimento.QUALQUER_INSTANTE, LocalDate.now()))
                .build();

        // Persiste a categoria
        entityManager.persist(categoria);
        entityManager.flush();
                
        
        //perisistindo uma conta        
        ContaNewDTO contaDTO = contaNewDTO(); 
        conta = Conta .builder() 
        		.id(UUID.fromString("00000000-0000-0000-0000-000000000001")) 
        		.agencia(contaDTO.getAgencia()) .banco(contaDTO.getBanco()) 
        		.cpfOuCnpj(contaDTO.getCpfOuCnpj()) .nome(contaDTO.getNome()) 
        		.numero(contaDTO.getNumero()) 
        		.saldo(contaDTO.getSaldo()) 
        		.status(true) .tipo(contaDTO.getTipo())
        		.build(); 
        entityManager.persist(conta); 
        entityManager.flush();
        
       //persistindo novo investimento
        InvestimentoNewDTO investimentoDTO = investimentoNewDTO();
        investimento = Investimento
        		.builder()
        		.categoria(categoria)
        		.conta(conta)
        		.indexador(categoriaDTO.getIndexador())
        		.instante(LocalDate.now())
        		.valor(investimentoDTO.getValor())
        		.build();
        
        entityManager.persist(investimento); 
        entityManager.flush();
       
    }

	
    @Test
    @DisplayName("Salvar uma categoria para que possa haver invetimento nela")
    void devePersistirCategoriaInvestimento() {
        CategoriaInvestimento categoriaSalva = entityManager.find(CategoriaInvestimento.class, categoria.getId());
        assertThat(categoriaSalva).isNotNull();
        assertThat(categoriaSalva.getDescricao()).isEqualTo("Investimento CDI");
        assertThat(categoriaSalva.getIndexador()).isEqualTo(Indexador.CDI);
    }

    @Test
    @DisplayName("Salvar uma conta para que ao investir, tenha conta com valor para poder investir")
    void devePersistirConta() {
        Conta contaSalva = entityManager.find(Conta.class, conta.getId());

       assertThat(contaSalva).isNotNull();
       assertThat(contaSalva.getCpfOuCnpj()).isEqualTo("12312312311");
    }

    @Test
    @DisplayName("Salva um novo investimento")
    void devePersistirInvestimento() {
        Investimento investimentoSalvo = entityManager.find(Investimento.class, investimento.getId());
       assertThat(investimentoSalvo).isNotNull();
       //verifica se o valor do investimento é menor que o saldo da conta
       assertThat(investimentoSalvo.getValor()).isLessThanOrEqualTo(conta.getSaldo());
       assertThat(investimentoSalvo.getConta()).isEqualTo(conta);
    }

    

    @Test
    @DisplayName("verifica se há investimentos pelo tipo de indexador")
    void deveEncontrarInvestimentosPorIndexador() {
        Page<Investimento> result = repository.findAllByIndexador(Indexador.CDI, PageRequest.of(0, 10));

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getIndexador()).isEqualTo(Indexador.CDI);
        assertThat(result.getContent().get(0).getCategoria().getDescricao()).isEqualTo("Investimento CDI");
    }
 
    


    private CategoriaInvestimentoNewDTO categoriaNewDTO() {
        return CategoriaInvestimentoNewDTO.builder()
                .indexador(Indexador.CDI)
                .dataInicio(LocalDate.now())
                .dataVencimento(LocalDate.now().plusDays(30)) // ou outro cálculo de vencimento
                .descricao("Investimento CDI")
                .percentualAdicional(BigDecimal.ZERO)
                .percentualIndexador(BigDecimal.valueOf(102))
                .risco(RiscoInvestimento.RISCO_BAIXO)
                .carencia(VencimentoInvestimento.QUALQUER_INSTANTE)
                .tipo(TipoInvestimento.CDB)
                .resgatavelAntecipadamente(true)
                .tipoRendimento(TipoRendimento.PREFIXADO)
                .build();
    }

    //regra para passar a carencia para uma carteina nova de investimento.
	
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

    
    private ContaNewDTO contaNewDTO() {
        return ContaNewDTO.builder()
                .nome("Fernando")
                .agencia(1)
                .numero(2)
                .banco(123)
                .saldo(BigDecimal.valueOf(1000))
                .cpfOuCnpj("12312312311")
                .tipo(TipoConta.CONTA_CORRENTE)
                .build();
    }


	private InvestimentoNewDTO investimentoNewDTO() {
		InvestimentoNewDTO investimento = InvestimentoNewDTO.builder()            
                .contaId(UUID.randomUUID())
                .categoriaId(UUID.randomUUID())
                .valor(BigDecimal.valueOf(100))
                .tipo(TipoInvestimento.CDB)
                .build();
		return investimento;
	}
	

}
