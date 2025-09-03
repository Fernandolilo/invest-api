import { Component, OnInit } from '@angular/core';
import { HomebrokerService } from '../../services/homebroker.service';
import { ipcaDTO } from '../../models/IpcaDTO';

@Component({
  selector: 'app-home-broker',
  templateUrl: './home-broker.component.html',
  styleUrls: ['./home-broker.component.scss']
})
export class HomeBrokerComponent implements OnInit {

  tickers: { tipo: string, texto: string }[] = [];
  currentIndex = 0;

  // Dados mock de CDI e Dólar
  dados = [
    { data: "01/09/2025", cdiDiario: 0.055131, cdiAnual: 14.899981 },
    { data: "01/09/2025", valorComercialVenda: 5.4372, valorComercialCompra: 5.4378, valorTurismoVenda: 5.7634, valorTurismoCompra: 5.7641 }
  ];

  constructor(private service: HomebrokerService) { }

  ngOnInit(): void {
    // Rodar ticker a cada 3 segundos
    setInterval(() => this.nextTicker(), 5000);

    // Carregar IPCA e adicionar CDI/Dólar
    this.loadTickers();
  }

  nextTicker() {
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
          texto: `💵 Dólar hoje ${res.data}: Venda: ${res.valorComercialVenda}, Compra: ${res.valorComercialCompra}, Turismo Venda: ${res.valorTurismoVenda}, Turismo Compra: ${res.valorTurismoCompra}`
        });
      },
      error: err => console.error('Erro ao buscar Dólar', err)
    });
  }


}
