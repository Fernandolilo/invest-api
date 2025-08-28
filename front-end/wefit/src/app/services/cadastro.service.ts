import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { cadastroDTO } from '../models/cadastroDTO';
import { enderecoDTO } from '../models/enderecoDTO';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {

  private readonly API = '/auth';

  constructor(private http: HttpClient) { }

  save(payload: { client: cadastroDTO, endereco: enderecoDTO }): Observable<any> {
    return this.http.post(`${this.API}/clients`, payload, {
      observe: 'response',
      responseType: 'json'
    });
  }



}
