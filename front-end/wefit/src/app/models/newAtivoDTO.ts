import { Data } from "@angular/router"

export interface newAtivo  {
  descricao: string,
  indexador: string,
  percentualAdicional: number,
  percentualIndexador: number,
  carencia : string,
  dataInicio: Data,
  dataVencimento: Data,
  tipo: string,
  tipoRendimento: string,
  risco: string,
  resgatavelAntecipadamente: boolean
}