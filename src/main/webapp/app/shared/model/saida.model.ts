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

export interface ISaida {
    id?: number;
    eminente?: string;
    razao?: string;
    valor?: number;
    forma?: Forma;
    data?: Moment;
    observacoes?: string;
    cor?: Cor;
}

export class Saida implements ISaida {
    constructor(
        public id?: number,
        public eminente?: string,
        public razao?: string,
        public valor?: number,
        public forma?: Forma,
        public data?: Moment,
        public observacoes?: string,
        public cor?: Cor
    ) {}
}
