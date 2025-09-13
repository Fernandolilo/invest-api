import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ipcaDTO } from '../models/IpcaDTO';
import { dolarDTo } from '../models/dolarDTO';
import { cdiDTO } from '../models/cdiDTO';

@Injectable({
  providedIn: 'root'
})
export class HomebrokerService {

  private readonly API = '/rendafixa';

  constructor(private http: HttpClient) { }

  getResumoIpca(): Observable<ipcaDTO> {
    return this.http.get<ipcaDTO>(`${this.API}/ipca/resumo`);
  }

  getDolar(): Observable<dolarDTo> {
    return this.http.get<dolarDTo>(`${this.API}/dollarXreal`);
  }

  getCadi(): Observable<cdiDTO> {
    return this.http.get<cdiDTO>(`${this.API}/cdi`);
  }
}
