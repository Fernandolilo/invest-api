import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'brl'
})
export class BrlPipe implements PipeTransform {

  transform(value: number | string | null | undefined): string {
    if (value == null || value === '') return '';

    const numberValue = typeof value === 'string' ? parseFloat(value) : value;

    return numberValue.toLocaleString('pt-BR', {
      style: 'currency',
      currency: 'BRL',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    });
  }


}
