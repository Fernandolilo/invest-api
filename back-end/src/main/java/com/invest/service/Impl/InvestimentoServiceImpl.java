package com.invest.service.Impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.invest.dto.ContaUpdateDTO;
import com.invest.dto.InvestimentoNewDTO;
import com.invest.entity.Client;
import com.invest.entity.Conta;
import com.invest.entity.Investimento;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.InvestimentoRepository;
import com.invest.service.ContaService;
import com.invest.service.InvestimentoService;
import com.invest.service.exeptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestimentoServiceImpl implements InvestimentoService {

    private final InvestimentoRepository repository;
    private final ClientRepository clientRepository;
    private final ModelMapper mapper;
    private final ContaService contaService;

    @Override
    public Investimento save(InvestimentoNewDTO obj) {

        Optional<Client> clientOpt = clientRepository.findByCpfOuCnpj(obj.getCpfOuCnpj());

        if (clientOpt.isEmpty()) {
            throw new ObjectNotFoundException("Cliente não encontrado: " + obj.getCpfOuCnpj());
        }

        List<Conta> contas = clientOpt.get().getContas();

        Conta contaSelecionada = contas.stream()
                .filter(c -> c.getId().equals(obj.getConta()))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada ou não pertence ao cliente"));

        BigDecimal saldoConta = contaSelecionada.getSaldo();
        BigDecimal valorInvestimento = obj.getValor();

        if (saldoConta == null) saldoConta = BigDecimal.ZERO;

        // Comparação correta com BigDecimal
        if (saldoConta.compareTo(valorInvestimento) < 0) {
            throw new ObjectNotFoundException("Saldo insuficiente para realizar o investimento.");
        }

        // Criando o investimento
        Investimento inv = mapper.map(obj, Investimento.class);
        inv.setConta(contaSelecionada);
        inv.setInstante(LocalDate.now());

        repository.save(inv);

        // Atualizando o saldo da conta corretamente
        BigDecimal novoSaldo = saldoConta.subtract(valorInvestimento);

        ContaUpdateDTO contaUpdateDTO = mapper.map(contaSelecionada, ContaUpdateDTO.class);
        contaUpdateDTO.setCpf(contaSelecionada.getClient().getCpfOuCnpj());
        contaUpdateDTO.setSaldo(novoSaldo);

        contaService.update(contaUpdateDTO);

        return inv;
    }
}
