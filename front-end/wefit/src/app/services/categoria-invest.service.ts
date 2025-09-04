import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { categoriaInvestDTO } from '../models/categoriaInvestDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoriaInvestService {

  private readonly API = '/api';

  constructor(private http: HttpClient) { }

  getCategoria(): Observable<categoriaInvestDTO[]> {
    return this.http.get<categoriaInvestDTO[]>(`${this.API}/rendafixa/categorias-investimentos/produtos`);
  }
}
