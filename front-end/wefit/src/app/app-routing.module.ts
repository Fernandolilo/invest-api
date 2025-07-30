import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { CadastroComponent } from './components/cadastro/cadastro.component';
import { HomeComponent } from './components/home/home.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ContaComponent } from './components/conta/conta.component';
import { ListInvestimentoComponent } from './components/list-investimento/list-investimento.component';
import { NewInvestCdi102Component } from './components/new-invest-cdi102/new-invest-cdi102.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: AuthComponent },
  { path: 'cadastro', component: CadastroComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'conta', component: ContaComponent },
  { path: 'new-invest-cdi102', component: NewInvestCdi102Component },
  { path: 'list-invest', component: ListInvestimentoComponent },
  { path: '**', redirectTo: 'login' } // <- sempre deixe essa por último
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
