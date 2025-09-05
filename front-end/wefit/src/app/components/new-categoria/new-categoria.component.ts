import { Component } from '@angular/core';
import { CategoriaInvestDTO } from '../../models/categoriaInvestDTO';

@Component({
  selector: 'app-new-categoria',
  templateUrl: './new-categoria.component.html',
  styleUrl: './new-categoria.component.scss'
})
export class NewCategoriaComponent {

  categoria: CategoriaInvestDTO = {
    descricao: '',
    percentualAdicional: 0,
    percentualIndexador: 0,
    carencia: '',
    dataInicio: '',
    dataVencimento: '',
    tipo: '',
    tipoRendimento: '',
    risco: ''
  };


}
