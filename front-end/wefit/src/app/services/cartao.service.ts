import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { newCartaoDTO } from '../models/cartaoNewDTO';
import { map, Observable, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartaoService {

  constructor() { }

  private http = inject(HttpClient);
  private router = inject(Router);

  private readonly API = '/api/invest';

  save(cartao: newCartaoDTO): Observable<any> {  // 👈 Aqui mudou
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

    return this.http.post(`${this.API}/cartoes`, cartao, {  // 👈 cartao direto
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
