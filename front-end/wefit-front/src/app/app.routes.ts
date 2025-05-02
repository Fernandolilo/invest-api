import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ListCadastroComponent } from './components/list-cadastro/list-cadastro.component';
import { CadastroComponent } from './components/cadastro/cadastro.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'cadastro', component: CadastroComponent },
  { path: 'lista/cadastro', component: ListCadastroComponent },
  { path: '**', redirectTo: 'login' }
];
