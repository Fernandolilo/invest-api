export interface requestContasDTO {
    numero: number,
    agencia: number,
    banco: number,
    saldo: number,
    tipo: string,
    nome: string,

    cpfOuCnpj: string
    selfie?: string;
}