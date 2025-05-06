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
      if (form.valid) {
        console.log('Credenciais:', this.creds);
    
        this.service.login(this.creds).subscribe({
          next: (response) => {

            this.router.navigate(['/cadastro']);
           
          },
          error: (err) => {
            console.error('Erro ao fazer login:', err);
          }
        });
      } else {
        console.log('Formulário inválido');
      }
    }
    
}
