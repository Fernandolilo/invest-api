import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CategoriaInvestDTO, NewCategoriaInvestDTO } from '../models/categoriaInvestDTO';
import { map, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoriaInvestService {
  private readonly API = '/rendafixa';

  constructor(private http: HttpClient) { }


  getCategoria(): Observable<CategoriaInvestDTO[]> {
    return this.http.get<CategoriaInvestDTO[]>(`${this.API}/categorias/produtos`);
  }

  save(categoria: NewCategoriaInvestDTO): Observable<any> {
    return this.http.post(`${this.API}/categorias`, categoria, {
      observe: 'response',
      responseType: 'json',
    });
  }

}
