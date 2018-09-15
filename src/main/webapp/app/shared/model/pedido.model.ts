import { Moment } from 'moment';
import { IItem } from 'app/shared/model//item.model';

export const enum Formato {
    COM_DESCARREGO = 'COM_DESCARREGO',
    SEM_DESCARREGO = 'SEM_DESCARREGO'
}

export interface IPedido {
    id?: number;
    formatoEntrega?: Formato;
    data?: Moment;
    valorUnitario?: number;
    prazo?: string;
    foiEntregue?: boolean;
    foiVisualizado?: boolean;
    saiuEntrega?: boolean;
    observacoes?: string;
    clienteId?: number;
    vendedorId?: number;
    items?: IItem[];
}

export class Pedido implements IPedido {
    constructor(
        public id?: number,
        public formatoEntrega?: Formato,
        public data?: Moment,
        public valorUnitario?: number,
        public prazo?: string,
        public foiEntregue?: boolean,
        public foiVisualizado?: boolean,
        public saiuEntrega?: boolean,
        public observacoes?: string,
        public clienteId?: number,
        public vendedorId?: number,
        public items?: IItem[]
    ) {
        this.foiEntregue = this.foiEntregue || false;
        this.foiVisualizado = this.foiVisualizado || false;
        this.saiuEntrega = this.saiuEntrega || false;
    }
}
