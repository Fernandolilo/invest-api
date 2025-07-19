import { Component, Input, OnInit } from '@angular/core';
import { ContasService } from '../../services/contas.service';
import { requestContasDTO } from '../../models/requestContasDTO';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  contas: requestContasDTO[] = [];

  constructor(private service: ContasService) { }
  ngOnInit(): void {
    this.service.findAllContas().subscribe(
      contas => {
        console.log('Contas carregadas:', contas);
        this.contas = Array.isArray(contas) ? contas : [contas]; // 👈 importante para garantir array
      },
      error => {
        console.error('Erro ao buscar contas:', error);
      }
    );
  }


}
