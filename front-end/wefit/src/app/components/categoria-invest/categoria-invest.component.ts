import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CategoriaInvestDTO } from '../../models/categoriaInvestDTO';
import { CategoriaInvestService } from '../../services/categoria-invest.service';

@Component({
  selector: 'app-categoria-invest',
  templateUrl: './categoria-invest.component.html',
  styleUrl: './categoria-invest.component.scss'
})
export class CategoriaInvestComponent implements OnInit {

  @Input() filtro: string | null = null;
  @Output() categoriasCarregadas: EventEmitter<CategoriaInvestDTO[]> = new EventEmitter<CategoriaInvestDTO[]>();

  categorias: CategoriaInvestDTO[] = [];

  constructor(private service: CategoriaInvestService) { }

  ngOnInit(): void {
    this.foundCategoria();
  }

  foundCategoria() {
    this.service.getCategoria().subscribe({
      next: res => {
        this.categorias = res;
        this.categoriasCarregadas.emit(res);
        console.log('Categorias carregadas:', this.categorias);
      },
      error: err => console.error('Erro ao buscar categorias', err)
    });
  }

  getRiscoClass(risco: string): string {
    switch (risco?.toUpperCase()) {
      case 'ALTO':
        return 'risco-alto';
      case 'MEDIO':
        return 'risco-medio';
      case 'BAIXO':
        return 'risco-baixo';
      default:
        return '';
    }
  }



}
