import { Component } from '@angular/core';
import { NewCategoriaInvestDTO } from '../../models/categoriaInvestDTO';
import { CategoriaInvestService } from '../../services/categoria-invest.service';

@Component({
  selector: 'app-new-categoria',
  templateUrl: './new-categoria.component.html',
  styleUrls: ['./new-categoria.component.scss'] // Corrigido de styleUrl para styleUrls
})
export class NewCategoriaComponent {

  categoria: NewCategoriaInvestDTO = this.getEmptyCategoria();

  alertMessage: string | null = null;
  alertType: 'success' | 'danger' = 'success';

  constructor(private service: CategoriaInvestService) { }

  /** Salva novo investimento */
  onNewInvestimento(): void {
    if (!this.isFormValid()) {
      this.alertType = 'danger';
      this.alertMessage = 'Preencha todos os campos obrigatórios corretamente.';
      return;
    }

    this.service.save(this.categoria).subscribe({
      next: (response) => {
        this.alertType = 'success';
        this.alertMessage = `Salvo com sucesso! Status: ${response.status}`;
        console.log('Resposta do backend:', response);

        this.resetForm();
      },
      error: (error) => {
        this.alertType = 'danger';
        this.alertMessage = 'Erro ao salvar categoria: ' +
          (error.error?.message || 'Verifique os dados e tente novamente.');
        console.error('Erro:', error);
      }
    });
  }

  /** Valida se todos os campos obrigatórios estão preenchidos */
  isFormValid(): boolean {
    return this.categoria.descricao.trim() !== '' &&
      this.categoria.indexador !== '' &&
      this.categoria.percentualIndexador !== null &&
      this.categoria.carencia !== '' &&
      this.categoria.tipo !== '' &&
      this.categoria.tipoRendimento !== '' &&
      this.categoria.risco !== '' &&
      this.categoria.resgatavelAntecipadamente !== null;
  }

  /** Reseta o formulário */
  resetForm(): void {
    this.categoria = this.getEmptyCategoria();
  }

  /** Retorna um objeto vazio para inicialização */
  private getEmptyCategoria(): NewCategoriaInvestDTO {
    return {
      descricao: '',
      indexador: '',
      percentualAdicional: 0,
      percentualIndexador: 0,
      carencia: '',
      tipo: '',
      tipoRendimento: '',
      risco: '',
      resgatavelAntecipadamente: true
    };
  }


}
