package com.syp.invest.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.syp.invest.entity.Investimento;
import com.syp.invest.entity.enums.Indexador;
import com.syp.invest.repositories.InvestimentoRepository;
import com.syp.invest.service.CdiService;
import com.syp.invest.service.DataService;
import com.syp.invest.service.JurusCdiTaskService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class JurusCdiTaskServiceImpl implements JurusCdiTaskService {

	  private final InvestimentoRepository repository;
	  private final CdiService cdiService;
	  private final DataService dataService;

	    /**
	     * Executa a cada 60 segundos (1 minuto).
	     * Exemplo de CRON: "0 0 2 * * ?" para rodar todo dia às 02:00 da manhã.
	     */
	    @Scheduled(cron = "0 * * * * ?")
	    @Override
	    public void sendJurus() {

	        int pageNumber = 0;
	        int pageSize = 100;
	       
	        
	        if(dataService.diaUtil().isDiaUtil() == true) {
	         
	     
	        
	        Page<Investimento> page;

	        do {
	        	 page = repository.findAllByIndexador(Indexador.valueOf("CDI"), PageRequest.of(pageNumber, pageSize));

	            for (Investimento inv : page.getContent()) {

	                BigDecimal valorAtual = inv.getValor(); // valor investido atual
	                BigDecimal cdiDiarioPercent = cdiService.foundCDI().getCdiDiario(); // Ex.: 0.055131 (% ao dia)

	                if (cdiDiarioPercent == null || valorAtual == null) {
	                    continue; // pula se dados inválidos
	                }

	                // 1️⃣ Converte de percentual para decimal: 0.055131% → 0.00055131
	                BigDecimal cdiDiarioDecimal = cdiDiarioPercent
	                        .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

	              
	                BigDecimal percentualIndexador = inv.getCategoria().getPercentualIndexador()
	                	    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

	                

	                BigDecimal taxaComBonus = cdiDiarioDecimal.multiply(percentualIndexador);


	                BigDecimal rendimento = valorAtual.multiply(taxaComBonus)
	                	    .setScale(6, RoundingMode.HALF_UP);
	                
	                BigDecimal novoValor = valorAtual.add(rendimento)
	                        .setScale(6, RoundingMode.HALF_UP);

	                inv.setValor(novoValor);
	                inv.setEvolucao(rendimento); // evolução do dia em reais
	            }

	            // Salva todos os investimentos atualizados
	            repository.saveAll(page.getContent());

	            pageNumber++;

	        } while (page.hasNext());
	    }
	 }
}
