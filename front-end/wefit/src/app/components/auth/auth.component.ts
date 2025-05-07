import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CredentialDTO } from '../../models/CredenciaisDTO';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent {

  
  creds: CredentialDTO = {
    email: '',
    password: '',
  };

  router = inject(Router);  // Injeção do Router para navegação
  private service = inject(AuthService);

  email: string | null = null;
  showPassword = false;

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  /*
  login(form: NgForm) {
    if (form.valid) {
      console.log('Credenciais:', this.creds);
      // Exemplo de redirecionamento após login bem-sucedido:
      this.router.navigate(['/cadastro']);
    } else {
      console.log('Formulário inválido');
    }
  }*/

    login(form: NgForm) {
      this.service.login(this.creds).subscribe(
        (res: any) => {
          const jwt = this.service.getToken();
          if (jwt) {
            this.router.navigate(['/cadastro'], {
              queryParams: {
                email: this.creds.email,
              }
            });
          } else {
            alert('Erro inesperado. Token não recebido.');
          }
        },
        error => {
          const errorList = error.error?.errors;
    
          if (Array.isArray(errorList) && errorList.length > 0) {
            alert(errorList[0]); // mostra apenas a primeira mensagem de erro
          } else {
            alert('Erro inesperado ao tentar fazer login.');
          }
        }
      );
    }   
}
