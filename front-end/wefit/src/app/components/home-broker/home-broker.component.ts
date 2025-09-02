import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-broker',
  templateUrl: './home-broker.component.html',
  styleUrl: './home-broker.component.scss'
})
export class HomeBrokerComponent implements OnInit {
  dados = [
    { data: "2025-07-01", mensal: 0.26, acumuladoAno: 5.23, acumulado12Meses: 101521 },
    { data: "01/09/2025", cdiDiario: 0.055131, cdiAnual: 14.899981 },
    { data: "01/09/2025", valorComercialVenda: 5.4372, valorComercialCompra: 5.4378, valorTurismoVenda: 5.7634, valorTurismoCompra: 5.7641 }
  ];

  currentIndex = 0;

  ngOnInit(): void {
    setInterval(() => this.nextData(), 3000); // troca a cada 3 segundos
  }

  nextData() {
    this.currentIndex = (this.currentIndex + 1) % this.dados.length; // loop infinito
  }
}
