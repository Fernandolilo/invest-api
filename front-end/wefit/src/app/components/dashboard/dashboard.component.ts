import { Component, Input, OnInit } from '@angular/core';
import { ContasService } from '../../services/contas.service';
import { requestContasDTO } from '../../models/requestContasDTO';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  contaSelecionada: any; // ou use um tipo adequado

  selecionarConta(conta: any): void {
    this.contaSelecionada = conta;
    // Se quiser navegar, adicione algo como:
    // this.router.navigate(['/home'], { queryParams: { contaId: conta.id } });
    console.log('Conta selecionada:', conta);
  }

  contas: requestContasDTO[] = [];

  constructor(private service: ContasService) { }
  ngOnInit(): void {
    this.service.findAllContas().subscribe(
      contas => {
        console.log('Contas carregadas:', contas);
        this.contas = Array.isArray(contas) ? contas : [contas]; // 👈 importante para garantir array
      },
      error => {
        console.error('Erro ao buscar contas:', error);
      }
    );
  }


}
