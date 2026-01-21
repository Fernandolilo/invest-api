import { Component } from '@angular/core';

@Component({
  selector: 'app-list-invest',
  templateUrl: './list-invest.component.html',
  styleUrl: './list-invest.component.scss'
})
export class ListInvestComponent {
 investimentos = [
    {
      valor: 100,
      evolucao: 0,
      instante: '2025-10-31',
      indexador: 'CDI',
      categoria: {
       
        descricao:
          'Este investimento rende 102% do CDI, com rentabilidade calculada sobre dias úteis. O Imposto de Renda incide apenas sobre os rendimentos e segue a tabela regressiva.',
        percentualAdicional: 1,
        percentualIndexador: 1,
        carencia: 'DIAS_30',
        indexador: 'CDI',
        dataInicio: '31/10/2025',
        dataVencimento: '30/11/2025',
        tipo: 'CDB',
        tipoRendimento: 'PREFIXADO',
        risco: 'RISCO_BAIXO',
        resgatavelAntecipadamente: true
      }
    }
  ];
}
