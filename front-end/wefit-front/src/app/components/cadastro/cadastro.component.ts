import { Component } from '@angular/core';
import { cadastroDTO } from '../../models/cadastro';
import { enderecoDTO } from '../../models/endereco';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss']
})
export class CadastroComponent {

  cad: cadastroDTO = {
    nome: '',
    cpfOuCnpj: '',
    celular: '',
    telefone: '',
    email: '',
    tipo: '',
    perfil: '',
    senha: '',
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

  save(form: NgForm): void {
    if (form.valid) {
      console.log('Dados do cadastro:', this.cad);
      console.log('Dados do endereço:', this.end);
      console.log('Confirmação:', this.cad.confirme ? 'Confirmado' : 'Não confirmado');
    } else {
      console.log('Formulário inválido');
    }
  }
  
}
