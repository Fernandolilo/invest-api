import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { depositoDTO } from '../../models/depositoDTO';
import { ContasService } from '../../services/contas.service';

@Component({
  selector: 'app-deposito',
  templateUrl: './deposito.component.html',
  styleUrl: './deposito.component.scss'
})
export class DepositoComponent implements OnInit {
  @Input() contaId: string = '';
  @Input() cpf: string = '';

  @Output() enviarDeposito = new EventEmitter<depositoDTO>();
  @Output() fecharDeposito = new EventEmitter<void>();

  deposito: depositoDTO = {
    id: '',
    saldo: 0,
    cpf: ''
  };

  constructor(private service: ContasService) { }

  ngOnInit() {
    this.deposito.id = this.contaId;
    this.deposito.cpf = this.cpf;
  }

  confirmarDeposito() {
    this.service.deposito(this.deposito).subscribe({
      next: () => {
        alert("Depósito executado com sucesso");
      },
      error: (err) => {
        console.error("Erro ao executar depósito:", err);
        alert("Erro ao executar depósito");
      }
    });
  }


  fechar() {
    this.fecharDeposito.emit();
  }
}
