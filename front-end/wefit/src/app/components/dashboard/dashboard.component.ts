import { Component, OnInit } from '@angular/core';
import { ContasService } from '../../services/contas.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  constructor(private service: ContasService) { }
  ngOnInit(): void {
    this.service.findAllContas().subscribe(
      contas => {
        console.log('Contas carregadas:', contas);
        // aqui você pode atribuir a uma variável local pra exibir no template
      },
      error => {
        console.error('Erro ao buscar contas:', error);
      }
    );
  }

}
