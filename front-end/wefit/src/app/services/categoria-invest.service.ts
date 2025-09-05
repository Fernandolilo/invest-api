import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CategoriaInvestDTO } from '../models/categoriaInvestDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoriaInvestService {

  private readonly API = '/api';

  constructor(private http: HttpClient) { }

  getCategoria(): Observable<CategoriaInvestDTO[]> {
    return this.http.get<CategoriaInvestDTO[]>(`${this.API}/rendafixa/categorias-investimentos/produtos`);
  }
}
