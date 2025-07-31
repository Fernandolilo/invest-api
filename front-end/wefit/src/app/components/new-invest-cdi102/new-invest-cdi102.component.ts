import { Component, inject, OnInit } from '@angular/core';
import { newInvestDTO } from '../../models/newInvestimentoDTO';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-new-invest-cdi102',
  templateUrl: './new-invest-cdi102.component.html',
  styleUrl: './new-invest-cdi102.component.scss'
})
export class NewInvestCdi102Component implements OnInit {

  router = inject(Router);
  route = inject(ActivatedRoute);
  id: string = '';

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const contaId = params['id'];
      this.id = contaId;
      if (contaId) {
        console.log(contaId)
      }
    });
  }


  newInvest: newInvestDTO = {
    valor: 0,
    cpfOuCnpj: '',
    conta: ''
  };

  onSubmit() {
    this.newInvest.conta = this.id;
    console.log(this.newInvest);
    // Aqui você pode chamar o service para enviar ao backend
  }
}
