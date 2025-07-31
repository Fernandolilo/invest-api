package com.invest.service.Impl;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.invest.entity.Investimento;
import com.invest.reposiotries.InvestimentoRepository;
import com.invest.service.CdiService;
import com.invest.service.JurusCdiTaskService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
class JurusCdiTaskServiceImple implements JurusCdiTaskService {

    private final InvestimentoRepository repository; 
    private final CdiService cdiService;

    // Executa a cada 60 segundos (o seu comentário dizia 10 segundos, mas o cron está 60s)
    @Scheduled(cron = "*/60 * * * * ?") 
    @Override
    public void sendJurus() {

        int pageNumber = 0;
        int pageSize = 100; 

        Page<Investimento> page;

        do {
            page = repository.findAll(PageRequest.of(pageNumber, pageSize));

            for (Investimento inv : page.getContent()) {
                BigDecimal atual = inv.getValor(); // valor atual do investimento

                // CDI diário já deve estar em BigDecimal
                BigDecimal taxaCdi = cdiService.foundCDI().getCdiDiario();

                // Aplica o multiplicador 1.02 usando BigDecimal
                BigDecimal taxaComBonus = taxaCdi.multiply(BigDecimal.valueOf(1.02));

                // Calcula novo valor
                BigDecimal novoValor = atual.add(taxaComBonus);

               // inv.setValor(novoValor);
                inv.setEvolucao(taxaComBonus);
            }

            repository.saveAll(page.getContent());

            pageNumber++;

        } while (page.hasNext());
    }

}
