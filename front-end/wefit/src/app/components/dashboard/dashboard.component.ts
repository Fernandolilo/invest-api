import { Component, inject, Input, OnInit } from '@angular/core';
import { ContasService } from '../../services/contas.service';
import { requestContasDTO } from '../../models/requestContasDTO';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  router = inject(Router);

  contaSelecionada: any; // ou use um tipo adequado

  contas: requestContasDTO[] = [];

  constructor(private service: ContasService) { }
  selecionarConta(conta: any): void {
    this.contaSelecionada = conta;

    this.service.findById(conta.id).subscribe({
      next: (res) => {
        console.log('Conta carregada via findById:', res);

        // Somente após confirmar que a conta existe, navegar:
        this.router.navigate(['/conta'], { queryParams: { id: conta.id } });
      },
      error: (err) => {
        console.error('Erro ao buscar conta por ID:', err);

        // Exemplo: exibir um alerta amigável
        alert('Conta não encontrada. Por favor, selecione outra.');

        // Ou redirecionar para uma página de erro personalizada
        // this.router.navigate(['/erro-conta-nao-encontrada']);
      }
    });
  }



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
