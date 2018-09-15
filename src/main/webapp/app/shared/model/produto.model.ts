export interface IProduto {
    id?: number;
    codigo?: string;
    nome?: string;
    tipo?: string;
    categoria?: string;
    peso?: string;
    marca?: string;
    descricao?: string;
    instrucoes?: string;
    tipoEmbalagem?: string;
}

export class Produto implements IProduto {
    constructor(
        public id?: number,
        public codigo?: string,
        public nome?: string,
        public tipo?: string,
        public categoria?: string,
        public peso?: string,
        public marca?: string,
        public descricao?: string,
        public instrucoes?: string,
        public tipoEmbalagem?: string
    ) {}
}
