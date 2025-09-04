import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HomebrokerService } from '../../services/homebroker.service';
import { categoriaInvestDTO } from '../../models/categoriaInvestDTO';
import { CategoriaInvestService } from '../../services/categoria-invest.service';

@Component({
  selector: 'app-list-investimento',
  templateUrl: './list-investimento.component.html',
  styleUrls: ['./list-investimento.component.scss']
})
export class ListInvestimentoComponent implements OnInit {

  tickers: { tipo: string, texto: string }[] = [];
  currentIndex = 0;



  router = inject(Router);
  route = inject(ActivatedRoute);
  tipoSelecionado: string | null = null;
  contaIdSelecionada: string = '';
  contaIdParam: string = '';

  invShow = false;
  ipcaShow = false;
  cdiShow = false;
  dolarShow = false;

  investimentos = [
    { nome: 'Renda Fixa', value: 'renda_fixa' },
    { nome: 'Tesouro Direto', value: 'tesouro' },
    { nome: 'Bolsa', value: 'bolsa' },
    { nome: 'CDI', value: 'cdi' },
    { nome: 'LCI', value: 'lci' },
    { nome: 'LCA', value: 'lca' }
  ];

  categorias: categoriaInvestDTO[] = [];

  constructor(
    private service: HomebrokerService,
    private CategoriaService: CategoriaInvestService) { }

  ngOnInit(): void {
    // Captura o ID que veio na URL
    this.route.queryParams.subscribe(params => {
      const contaId = params['id'];
      this.contaIdParam = contaId;
    });

    // Rodar ticker a cada 3 segundos
    setInterval(() => this.nextTicker(), 5000);

    // Carregar IPCA e adicionar CDI/Dólar
    this.loadTickers();

    this.foundCategoria();
  }

  selecionarInvestimento(valor: string) {
    this.tipoSelecionado = valor;
    console.log('Tipo selecionado:', this.tipoSelecionado);
    console.log('Conta ID:', this.contaIdParam);
    this.foundCategoria();
    this.invShow = true;
  }

  onInvestCdi102() {
    console.log('Conta ID:', this.contaIdParam);

    // Navega para rota já levando o parâmetro
    this.router.navigate(['/new-invest-cdi102'], {
      queryParams: { id: this.contaIdParam }
    });
  }



  nextTicker() {
    if (!this.tickers.length) return;

    // Primeiro, desativa todos
    this.ipcaShow = false;
    this.cdiShow = false;
    this.dolarShow = false;

    // Ativa o card correspondente ao ticker atual
    const ticker = this.tickers[this.currentIndex];
    if (ticker.tipo === 'ipca') this.ipcaShow = true;
    if (ticker.tipo === 'cdi') this.cdiShow = true;
    if (ticker.tipo === 'dolar') this.dolarShow = true;

    // Passa para o próximo índice
    this.currentIndex = (this.currentIndex + 1) % this.tickers.length;
  }


  loadTickers() {
    // 1️⃣ IPCA
    this.service.getResumoIpca().subscribe({
      next: res => {
        this.tickers.push({
          tipo: 'ipca',
          texto: `📊 IPCA hoje ${res.data}: Mensal: ${res.mensal}%, Acum. Ano: ${res.acumuladoAno}%, Acum. 12M: ${res.acumulado12Meses}`
        });
      },
      error: err => console.error('Erro ao buscar IPCA', err)
    });

    // 2️⃣ CDI
    this.service.getCadi().subscribe({
      next: res => {
        this.tickers.push({
          tipo: 'cdi',
          texto: `💹 CDI hoje ${res.data}: CDI Diário: ${res.cdiDiario}%, CDI Anual: ${res.cdiAnual}%`
        });
      },
      error: err => console.error('Erro ao buscar CDI', err)
    });

    // 3️⃣ Dólar
    this.service.getDolar().subscribe({
      next: res => {
        this.tickers.push({
          tipo: 'dolar',
          texto: `💵 Dólar hoje ${res.data}: Venda: ${res.valorComercialVenda}, Compra: ${res.valorComercialCompra},
           Turismo Venda: ${res.valorTurismoVenda}, Turismo Compra: ${res.valorTurismoCompra}`
        });
      },
      error: err => console.error('Erro ao buscar Dólar', err)
    });
  }

  foundCategoria() {
    this.CategoriaService.getCategoria().subscribe({
      next: res => {
        this.categorias = res;
        console.log('Categorias carregadas:', this.categorias);
      },
      error: err => console.error('Erro ao buscar categorias', err)
    });
  }

  getRiscoClass(risco: string): string {
    switch (risco?.toUpperCase()) {
      case 'ALTO':
        return 'risco-alto';
      case 'MEDIO':
        return 'risco-medio';
      case 'BAIXO':
        return 'risco-baixo';
      default:
        return '';
    }
  }

  onCardClick() {
    this.selecionarInvestimento('cdi');
    this.foundCategoria();
  }

  voltar() {
    this.invShow = false;
    this.tipoSelecionado = null;
  }

  getTickerTexto(tipo: string): string {
    const ticker = this.tickers.find(t => t.tipo === tipo);
    return ticker ? ticker.texto : '';
  }



}
