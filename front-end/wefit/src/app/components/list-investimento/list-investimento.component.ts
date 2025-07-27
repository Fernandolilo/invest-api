import { Component } from '@angular/core';

@Component({
  selector: 'app-list-investimento',
  templateUrl: './list-investimento.component.html',
  styleUrl: './list-investimento.component.scss'
})
export class ListInvestimentoComponent {

  tipoSelecionado: string = '';

  onSelecionarInvestimento(): void {
    console.log('Tipo selecionado:', this.tipoSelecionado);
  }

}
