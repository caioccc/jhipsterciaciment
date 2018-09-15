import { Moment } from 'moment';

export const enum Forma {
    CHEQUE = 'CHEQUE',
    DUPLICATA = 'DUPLICATA',
    AVISTA = 'AVISTA',
    BOLETO = 'BOLETO',
    DINHEIRO = 'DINHEIRO',
    CARTAO = 'CARTAO'
}

export const enum Cor {
    VERMELHO = 'VERMELHO',
    VERDE = 'VERDE',
    AZUL = 'AZUL',
    AMARELO = 'AMARELO',
    ROXO = 'ROXO'
}

export interface IEntrada {
    id?: number;
    cliente?: string;
    valorTotal?: number;
    valorPago?: number;
    data?: Moment;
    formaPagamento?: Forma;
    cor?: Cor;
}

export class Entrada implements IEntrada {
    constructor(
        public id?: number,
        public cliente?: string,
        public valorTotal?: number,
        public valorPago?: number,
        public data?: Moment,
        public formaPagamento?: Forma,
        public cor?: Cor
    ) {}
}
