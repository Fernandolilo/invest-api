export interface CategoriaInvestDTO {
    id: string;
    descricao: string;
    percentualAdicional: number;
    percentualIndexador: number;
    carencia: string;
    dataInicio: string;
    dataVencimento: string;
    tipo: string;
    tipoRendimento: string;
    risco: string;
}

export interface NewCategoriaInvestDTO {
    descricao: string;
    indexador: string;
    percentualAdicional: number;
    percentualIndexador: number;
    carencia: string;
    tipo: string;
    tipoRendimento: string;
    risco: string;
    resgatavelAntecipadamente: boolean;
}


