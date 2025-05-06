import { inject, Injectable } from '@angular/core';
import { CredentialDTO } from '../models/CredenciaisDTO';
import { map, Observable, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  private http = inject(HttpClient);

  private readonly API = 'api/wefit';



  login(credenciais: CredentialDTO): Observable<void> {
    const body = {
      email: credenciais.email,
      password: credenciais.password
    };
  
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': '*/*'
    });
  
    return this.http.post(`${this.API}/clients/authenticate`, body, {
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
  

}
