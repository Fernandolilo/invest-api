import { Component } from '@angular/core';
import { requestContasDTO } from '../../models/requestContasDTO';
import { ActivatedRoute } from '@angular/router';
import { ContasService } from '../../services/contas.service';
import { cadastroDTO } from '../../models/cadastroDTO';
import { newCartaoDTO } from '../../models/cartaoNewDTO';

@Component({
  selector: 'app-conta',
  templateUrl: './conta.component.html',
  styleUrl: './conta.component.scss'
})
export class ContaComponent {
  mostrarSaldo = true;
  mostrarDadosConta = true;
  mostrarExtrato = true;
  solicitarCartao = true;
  bandeiraSelecionada: string = '';


  cartao: newCartaoDTO = {
    nomeTitular: '',
    tipo: '',
    bandeira: '',
    senha: '',
    contaId: ''
  };

  contaSelecionada: requestContasDTO | null = null;

  constructor(
    private route: ActivatedRoute,
    private service: ContasService) { }


  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const contaId = params['id'];
      if (contaId) {
        this.service.findById(contaId).subscribe({
          next: (conta) => {
            this.contaSelecionada = conta;
            console.log('Conta carregada:', conta);
          },
          error: (err) => {
            console.error('Erro ao buscar conta por ID:', err);
            alert('Conta não encontrada ou ocorreu um erro ao carregar os dados.');
          }
        });
      }
    });
  }


  alternarSaldo(): void {
    this.mostrarSaldo = !this.mostrarSaldo;
  }

  onVisualConta(): void {
    this.mostrarDadosConta = !this.mostrarDadosConta;
  }
  onExtrato(): void {
    this.mostrarExtrato = !this.mostrarExtrato;
  }
  onSolicitarCartao(): void {
    this.solicitarCartao = !this.solicitarCartao;
  }

  onCartao(): void {
    if (this.cartao && this.contaSelecionada) {
      this.cartao.nomeTitular = this.contaSelecionada.nome;
      this.cartao.contaId = this.contaSelecionada.id;
    }

    console.log(this.cartao);
  }
}
