import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { newInvestDTO } from '../models/newInvestimentoDTO';
import { map, Observable, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AplicarCdiService {


  private http = inject(HttpClient);


  private readonly API = '/api/invest';

  constructor() { }



  newInvestCdi102(investimento: newInvestDTO): Observable<void> {
    const body = {
      valor: investimento.valor,
      cpfOuCnpj: investimento.cpfOuCnpj,
      conta: investimento.conta
    };

    const token = localStorage.getItem('Authorization');
    if (!token) {
      return throwError(() => new Error('No token found in local storage'));
    }

    const jwt = token.substring(7); // Remove "Bearer " prefix

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${jwt}`,
      'Content-Type': 'application/json',
      'Accept': '*/*'
    });


    return this.http.post(`${this.API}/investimentos`, body, {
      observe: 'response',
      responseType: 'json',
      headers
    }).pipe(
      tap(response => {
        const newToken = response.headers.get('Authorization');
        if (newToken) {
          localStorage.setItem('Authorization', newToken);
        } else {
          console.warn('Token não encontrado no header!');
        }
      }),
      map(() => void 0)
    );
  }
}
