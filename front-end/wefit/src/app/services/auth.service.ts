import { inject, Injectable } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { CredentialDTO } from '../models/CredenciaisDTO';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  private http = inject(HttpClient);
  private router = inject(Router);

  private readonly API = '/auth';

  login(credenciais: CredentialDTO): Observable<void> {
    const body = {
      email: credenciais.email,
      password: credenciais.password
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': '*/*'
    });

    return this.http.post(`${this.API}/authenticate`, body, {
      headers,
      observe: 'response'
    }).pipe(
      tap(response => {
        const token = response.headers.get('Authorization');
        if (token) {
          localStorage.setItem('Authorization', token);
        } else {
          console.warn('Token não encontrado no header!');
        }
      }),
      map(() => void 0) // retorna apenas void
    );
  }


  getToken(): string | null {
    return localStorage.getItem('Authorization');
  }

  // Método para logout, limpa o token e redireciona para login
  logout(): void {
    if (typeof window !== 'undefined') {
      localStorage.clear(); // Limpa tudo no localStorage
      localStorage.removeItem('Authorization'); // Remove o token específico
      this.router.navigate(['/login']); // Redireciona para a página de login

    }

  }


  isAuthenticateds(): boolean {
    if (typeof window !== 'undefined' && window.localStorage) {
      const token = localStorage.getItem('Authorization');
      return !!token;
    }
    return false;
  }


}
