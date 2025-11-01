import { Component, inject } from '@angular/core';
import { requestContasDTO } from '../../models/requestContasDTO';
import { ActivatedRoute, Router } from '@angular/router';
import { ContasService } from '../../services/contas.service';
import { newCartaoDTO } from '../../models/cartaoNewDTO';
import { CartaoService } from '../../services/cartao.service';
import { depositoDTO } from '../../models/depositoDTO';

@Component({
  selector: 'app-conta',
  templateUrl: './conta.component.html',
  styleUrl: './conta.component.scss'
})
export class ContaComponent {

  mostrarDeposito = false;

  mostrarSaldo = true;
  mostrarDadosConta = true;
  mostrarExtrato = true;
  solicitarCartao = true;
  bandeiraSelecionada: string = '';
  contaId: string = '';


  cartao: newCartaoDTO = {
    nomeTitular: '',
    tipo: '',
    bandeira: '',
    senha: '',
    contaId: ''
  };

  contaSelecionada: requestContasDTO | null = null;

  router = inject(Router);

  constructor(
    private route: ActivatedRoute,
    private service: ContasService,
    private cartaoService: CartaoService) { }


  ngOnInit(): void {
    this.solicitarCartao = false; // fecha o formulário no início
    this.mostrarExtrato = false;
    this.mostrarDadosConta = false;

    this.route.queryParams.subscribe(params => {
      const contaId = params['id'];
      this.cartao.contaId = contaId;
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

  abrirDeposito() {
    this.mostrarDeposito = true;
  }

  receberDeposito(dados: depositoDTO) {
    console.log('Depósito recebido do filho:', dados);

    // Aqui você pode chamar o service para salvar no backend
    // this.depositoService.salvar(dados).subscribe(...)
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

  onNewCard() {
    this.onSolicitarCartao();
  }
  onVerExtrato() {
    this.onExtrato();
  }

  onCartao(): void {
    if (this.cartao && this.contaSelecionada) {
      this.cartao.nomeTitular = this.contaSelecionada.nome;
      this.cartao.contaId = this.contaSelecionada.id;

      this.cartaoService.save(this.cartao).subscribe({
        next: () => {
          alert('Cartão salvo com sucesso');
        },
        error: (err) => {
          // Tenta extrair a mensagem do backend
          const errorMessage =
            err?.error?.message || // Caso o backend envie { message: "..." }
            err?.error?.error ||   // Caso envie { error: "..." }
            err?.message ||        // Mensagem padrão
            'Erro desconhecido ao salvar o cartão.';

          alert(`Erro ao salvar o cartão: ${errorMessage}`);
          console.error('Detalhes do erro:', err);
        }
      });
    } else {
      console.warn('Cartão ou conta não definidos.');
    }
  }

  onNewInvest(event?: Event) {
    event?.preventDefault(); // Evita comportamento padrão do <a href="#">
    console.log(this.cartao.contaId);
    this.router.navigate(['/list-invest'], {
      queryParams: { id: this.cartao.contaId }
    });
  }
  onListInvest() {
    this.router.navigateByUrl("/investimentos");
  }

}
