import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { depositoDTO } from '../../models/depositoDTO';

@Component({
  selector: 'app-deposito',
  templateUrl: './deposito.component.html',
  styleUrl: './deposito.component.scss'
})
export class DepositoComponent implements OnInit {
  @Input() contaId: string = '';
  @Input() cpfOuCnpj: string = '';

  @Output() enviarDeposito = new EventEmitter<depositoDTO>();
  @Output() fecharDeposito = new EventEmitter<void>();

  deposito: depositoDTO = {
    id: '',
    saldo: 0,
    cpfOuCnpj: ''
  };

  ngOnInit() {
    this.deposito.id = this.contaId;
    this.deposito.cpfOuCnpj = this.cpfOuCnpj;
  }

  confirmarDeposito() {
    console.log('Enviando depósito:', this.deposito);
    // Aqui você chama o service para enviar para o backend
  }
  fechar() {
    this.fecharDeposito.emit();
  }
}
