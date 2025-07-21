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
    senha: '',
    confirme: false,
    selfie: ''
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
  selfieDataUrl: string = '';

  step: number = 1;
  constructor(private service: CadastroService, private http: HttpClient) { }



  // Método chamado quando a selfie for capturada
  onSelfieCaptured(dataUrl: string): void {
    this.selfieDataUrl = dataUrl;
    this.cad.selfie = dataUrl;
  }

  isStep1Valid(): boolean {
    return (
      !!this.cad.nome && this.cad.nome.length >= 3 &&
      !!this.cad.cpfOuCnpj &&
      !!this.cad.celular
    );
  }

  isStep2Valid(): boolean {
    return (
      !!this.end.cep &&
      !!this.end.logradouro &&
      !!this.end.numero &&
      !!this.end.bairro &&
      !!this.end.cidade &&
      !!this.end.estado
    );
  }


  validateEmail(email: string): boolean {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email.toLowerCase());
  }

  goToStep1(): void {
    this.step = 1;
  }

  goToStep2(): void {
    if (this.isStep1Valid()) {
      this.step = 2;
    } else {
      alert('Por favor, preencha todos os campos obrigatórios corretamente antes de continuar.');
    }
  }

  goToStep3(): void {
    if (this.isStep2Valid()) {
      this.step = 3;
    } else {
      alert('Por favor, preencha corretamente o e-mail, a senha e aceite os termos antes de continuar.');
    }
  }


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
