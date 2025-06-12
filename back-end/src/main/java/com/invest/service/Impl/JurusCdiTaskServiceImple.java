package com.invest.service.Impl;

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

    private final InvestimentoRepository repository; // final para injeção via constructor
    private final CdiService cdiService;

    @Scheduled(cron = "*/60 * * * * ?") // Executa a cada 10 segundos
    @Override
    public void sendJurus() { // Sem parâmetros!

        int pageNumber = 0;
        int pageSize = 100; // tamanho da página

        Page<Investimento> page;

        do {
            page = repository.findAll(PageRequest.of(pageNumber, pageSize));

            for (Investimento inv : page.getContent()) {
                double atual = inv.getValor();
               
                double taxa =cdiService.foundCDI().getCdiDiario()  *1.02;
                
                double acrescimo = taxa; // 1% de acréscimo
                
                inv.setValor(atual + acrescimo);
            }

            repository.saveAll(page.getContent());

            pageNumber++;

        } while (page.hasNext());

    }
}
