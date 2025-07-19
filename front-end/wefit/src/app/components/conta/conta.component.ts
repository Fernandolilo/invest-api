import { Component } from '@angular/core';

@Component({
  selector: 'app-conta',
  templateUrl: './conta.component.html',
  styleUrl: './conta.component.scss'
})
export class ContaComponent {
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
