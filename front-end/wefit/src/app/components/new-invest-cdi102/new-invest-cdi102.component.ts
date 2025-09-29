import { Component, inject, OnInit } from '@angular/core';
import { newInvestDTO } from '../../models/newInvestimentoDTO';
import { ActivatedRoute, Router } from '@angular/router';
import { AplicarCdiService } from '../../services/aplicar-cdi.service';

@Component({
  selector: 'app-new-invest-cdi102',
  templateUrl: './new-invest-cdi102.component.html',
  styleUrl: './new-invest-cdi102.component.scss'
})
export class NewInvestCdi102Component implements OnInit {

  router = inject(Router);
  route = inject(ActivatedRoute);
  id: string = '';
  objetoRecebido: any;

  constructor(private service: AplicarCdiService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['data']) {
        this.objetoRecebido = JSON.parse(params['data']);
        console.log('Recebido:', this.objetoRecebido);

        // ⚡ atualiza o newInvest **depois** que o objeto é recebido
        this.newInvest.contaId = this.objetoRecebido.contaId;
        this.newInvest.tipo = this.objetoRecebido.tipo;
        this.newInvest.categoriaId = this.objetoRecebido.categoriaId;

        console.log('newInvest atualizado:', this.newInvest);
      }
    });
  }


  newInvest: newInvestDTO = {
    valor: 0,
    cpfOuCnpj: '',
    contaId: '',
    categoriaId: '',
    tipo: ''
  };

  onSubmit() {
    console.log('Payload final:', this.newInvest);

    this.service.newInvestCdi102(this.newInvest).subscribe({
      next: () => alert("Investimento realizado com sucesso!"),
      error: () => alert("Tente novamente!")
    });
  }
}
