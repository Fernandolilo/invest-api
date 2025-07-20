import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, map, Observable, tap, throwError } from 'rxjs';
import { requestContasDTO } from '../models/requestContasDTO';

@Injectable({
  providedIn: 'root'
})
export class ContasService {

  constructor() { }

  private http = inject(HttpClient);
  private router = inject(Router);

  private readonly API = '/api/invest';

  //requestContasDTO 

  findAllContas(): Observable<requestContasDTO[]> {
    const token = localStorage.getItem('Authorization');
    if (!token) {
      return throwError(() => new Error('No token found in local storage'));
    }

    const jwt = token.substring(7); // Remova o prefixo "Bearer " do token

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${jwt}`,
      'Content-Type': 'application/json',
      'Accept': '*/*'
    });

    return this.http.get<requestContasDTO[]>(`${this.API}/contas/cliente`, { headers }).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erro ao buscar tanques:', error);
        return throwError(() => new Error('Error during the request'));
      }));
  }


  findById(id: string): Observable<requestContasDTO> {
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

    return this.http.get<requestContasDTO>(`${this.API}/contas/${id}`, { headers }).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erro ao buscar conta por ID:', error);
        return throwError(() => new Error('Error during the request'));
      })
    );
  }


}
