import { Component, inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router, RouterOutlet } from '@angular/router';  // Importando RouterOutlet e Router
import { CommonModule } from '@angular/common';  // Importando CommonModule
import { FormsModule } from '@angular/forms';  // Importando FormsModule
import { CredenciaisDTO } from '../../models/CredenciaisDTO';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet],  // Adicionando os módulos necessários
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  
  creds: CredenciaisDTO = {
    email: '',
    password: '',
  };

  router = inject(Router);  // Injeção do Router para navegação

  email: string | null = null;
  showPassword = false;

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  login() {
    if (this.creds && this.creds.email && this.creds.password) {
      console.log('Credenciais:', this.creds);
      this.router.navigateByUrl('/cadastro');
    } else {
      console.log('Formulário inválido');
    }
  }
  
  
}
