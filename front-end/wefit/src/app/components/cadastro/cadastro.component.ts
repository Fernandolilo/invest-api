import { Component, inject } from '@angular/core';
import { cadastroDTO } from '../../models/cadastroDTO';
import { enderecoDTO } from '../../models/enderecoDTO';
import { CadastroService } from '../../services/cadastro.service';
import { HttpClient } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.scss'
})
export class CadastroComponent {

  router = inject(Router);

  cad: cadastroDTO = {
    nome: '',
    cpfOuCnpj: '',
    celular: '',
    telefone: '',
    email: '',
    tipo: '',
    perfil: '',
    senha: '',
    role: '',
    confirme: false,
  }

  end: enderecoDTO = {
    cep: '',
    logradouro: '',
    numero: '',
    complemento: '',
    bairro: '',
    cidade: '',
    estado: ''
  }

  constructor(private service: CadastroService, private http: HttpClient) { }



  save(form: NgForm): void {
    if (form.valid) {
      if (this.cad.tipo === 'fisica') {
        this.cad.tipo = 'PESSOA_FISICA';
      } else if (this.cad.tipo === 'juridica') {
        this.cad.tipo = 'PESSOA_JURIDICA';
      }



      const payload = {
        client: this.cad,
        endereco: this.end
      };

      if (!payload.client.confirme) {
        alert('Você precisa confirmar os dados e aceitar os termos antes de prosseguir.');
        return; // Interrompe a execução aqui
      }
      this.service.save(payload).subscribe(
        (response: any) => {  // Tipagem explícita para o response
          alert('Cadastro realizado com sucesso!');
          this.router.navigateByUrl("/login");

        },
        (error: any) => {  // Tipagem explícita para o error
          console.error('Erro ao cadastrar', error);
        }
      );
    } else {
      console.log('Formulário inválido');
    }
  }

}
