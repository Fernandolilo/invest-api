import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './components/auth/auth.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CadastroComponent } from './components/cadastro/cadastro.component';
import { HomeComponent } from './components/home/home.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ContaComponent } from './components/conta/conta.component';
import { CameraComponent } from './components/camera/camera.component';
import { ListInvestimentoComponent } from './components/list-investimento/list-investimento.component';
import { NewInvestCdi102Component } from './components/new-invest-cdi102/new-invest-cdi102.component';
import { DepositoComponent } from './components/deposito/deposito.component';
import { CategoriaInvestComponent } from './components/categoria-invest/categoria-invest.component';
import { BrlPipe } from './pipes/brl.pipe';
import { NewCategoriaComponent } from './components/new-categoria/new-categoria.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    CadastroComponent,
    HomeComponent,
    DashboardComponent,
    ContaComponent,
    CameraComponent,
    ListInvestimentoComponent,
    NewInvestCdi102Component,
    DepositoComponent,
    CategoriaInvestComponent,
    BrlPipe,
    NewCategoriaComponent,

  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
