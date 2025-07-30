import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-investimento',
  templateUrl: './list-investimento.component.html',
  styleUrls: ['./list-investimento.component.scss']
})
export class ListInvestimentoComponent {
  tipoSelecionado: string = '';
  router = inject(Router);

  investimentos = [
    { nome: 'Renda Fixa', value: 'renda_fixa' },
    { nome: 'Tesouro Direto', value: 'tesouro' },
    { nome: 'Bolsa', value: 'bolsa' },
    { nome: 'CDI', value: 'cdi' },
    { nome: 'LCI', value: 'lci' },
    { nome: 'LCA', value: 'lca' }
  ];

  selecionarInvestimento(valor: string) {
    this.tipoSelecionado = valor;
    console.log('Investimento selecionado:', this.tipoSelecionado);
  }

  onInvestCdi102() {
    this.router.navigate(['/new-invest-cdi102']);
  }
}
