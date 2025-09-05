import { Component, inject, Input, OnInit } from '@angular/core';
import { ContasService } from '../../services/contas.service';
import { requestContasDTO } from '../../models/requestContasDTO';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  router = inject(Router);

  contaSelecionada: any; // ou use um tipo adequado

  contas: requestContasDTO[] = [];
  role: string = '';
  isAdmin: boolean = false;
  menuOpen: boolean = false;

  constructor(
    private service: ContasService,
    private authService: AuthService) { }


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
    //this.menuOpen = false;
    this.onFindAutorizacao();
    this.onFindConta();
  }

  onFindAutorizacao() {
    this.authService.findRole().subscribe(
      next => {
        this.role = next;
        this.isAdmin = this.role.includes('ROLE_ADMIN');
        this.onAdm();
      },
      error => {
        console.error("erro ao buscar autorizacao!");
      }
    )
  }

  onFindConta() {
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

  onAdm() {
    console.log("Usuário é ADMIN " + this.role);
  }

  abrirMenuAdmin() {
    console.log("Menu admin aberto!");
    this.menuOpen = !this.menuOpen;
    // aqui você pode abrir modal, dropdown ou navegar para página admin
    console.log("Menu  " + this.menuOpen)
  }

  fecharMenuAdmin() {
    this.menuOpen = false; // fecha menu
  }

  opcao1() {
    console.log('Opção 1 selecionada');
    this.fecharMenuAdmin();
  }

  opcao2() {
    console.log('Opção 2 selecionada');
    this.fecharMenuAdmin();
  }

  opcao3() {
    console.log('Opção 3 selecionada');
    this.fecharMenuAdmin();
  }


}
