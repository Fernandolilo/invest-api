import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  mostrarSaldo = true;
  mostrarDadosConta = true;
  mostrarExtrato = true;

  alternarSaldo(): void {
    this.mostrarSaldo = !this.mostrarSaldo;
  }

  onVisualConta(): void {
    this.mostrarDadosConta = !this.mostrarDadosConta;
  }
  onExtrato(): void {
    this.mostrarExtrato = !this.mostrarExtrato;
  }
}
