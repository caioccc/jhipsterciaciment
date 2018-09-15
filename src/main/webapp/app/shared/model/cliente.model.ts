import { IPedido } from 'app/shared/model//pedido.model';

export interface ICliente {
    id?: number;
    nome?: string;
    telefone?: string;
    endereco?: string;
    pedidos?: IPedido[];
}

export class Cliente implements ICliente {
    constructor(public id?: number, public nome?: string, public telefone?: string, public endereco?: string, public pedidos?: IPedido[]) {}
}
