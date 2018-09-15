export interface IItem {
    id?: number;
    quantidade?: string;
    valorItem?: number;
    pedidoId?: number;
    produtoId?: number;
}

export class Item implements IItem {
    constructor(
        public id?: number,
        public quantidade?: string,
        public valorItem?: number,
        public pedidoId?: number,
        public produtoId?: number
    ) {}
}
