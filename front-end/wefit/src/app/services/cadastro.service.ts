import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { cadastroDTO } from '../models/cadastroDTO';
import { enderecoDTO } from '../models/enderecoDTO';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {

  private readonly API = 'api/wefit';

  constructor(private http: HttpClient) { }

  save(payload: { client: cadastroDTO, endereco: enderecoDTO }): Observable<any> {
    
    const token = localStorage.getItem('Authorization'); // Pegue o token do local storage
    if (!token) {
      return throwError(() => new Error('No token found in local storage'));
    }
    const jwt = token.replace('Bearer ', ''); // Ajustado para remover o 'Bearer ' do token
    console.log(jwt);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${jwt}`,
      'Content-Type': 'application/json',
      'Accept': '*/*'
    });


  
    return this.http.post(`${this.API}/clients`, payload, {
      headers,
      observe: 'response',
      responseType: 'json'
    });
  }
  


}
