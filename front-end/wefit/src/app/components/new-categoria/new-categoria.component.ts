import { Component } from '@angular/core';
import { NewCategoriaInvestDTO } from '../../models/categoriaInvestDTO';

@Component({
  selector: 'app-new-categoria',
  templateUrl: './new-categoria.component.html',
  styleUrl: './new-categoria.component.scss'
})
export class NewCategoriaComponent {


  categoria: NewCategoriaInvestDTO = {
    descricao: '',
    indexador: '',
    percentualAdicional: 0,
    percentualIndexador: 0,
    carencia: '',
    dataInicio: '',
    dataVencimento: '',
    tipo: '',
    tipoRendimento: '',
    risco: '',
    resgatavelAntecipadamente: true
  };

  onNewInvestimento() {
    console.log('Valores do formulário:', this.categoria);
    // aqui você pode enviar para o backend
  }
}