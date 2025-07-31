import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-list-investimento',
  templateUrl: './list-investimento.component.html',
  styleUrls: ['./list-investimento.component.scss']
})
export class ListInvestimentoComponent implements OnInit {


  router = inject(Router);
  route = inject(ActivatedRoute);
  tipoSelecionado: string = '';
  contaIdSelecionada: string = '';
  contaIdParam: string = '';

  investimentos = [
    { nome: 'Renda Fixa', value: 'renda_fixa' },
    { nome: 'Tesouro Direto', value: 'tesouro' },
    { nome: 'Bolsa', value: 'bolsa' },
    { nome: 'CDI', value: 'cdi' },
    { nome: 'LCI', value: 'lci' },
    { nome: 'LCA', value: 'lca' }
  ];

  ngOnInit(): void {
    // Captura o ID que veio na URL
    this.route.queryParams.subscribe(params => {
      const contaId = params['id'];
      this.contaIdParam = contaId;
      if (contaId) {
        console.log(contaId);
      }
    });
  }

  selecionarInvestimento(valor: string) {
    this.tipoSelecionado = valor;
    console.log('Tipo selecionado:', this.tipoSelecionado);
    console.log('Conta ID:', this.contaIdParam);
  }

  onInvestCdi102() {
    console.log('Conta ID:', this.contaIdParam);

    // Navega para rota já levando o parâmetro
    this.router.navigate(['/new-invest-cdi102'], {
      queryParams: { id: this.contaIdParam }
    });
  }
}
