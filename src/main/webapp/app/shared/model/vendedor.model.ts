import { ICliente } from 'app/shared/model//cliente.model';

export interface IVendedor {
    id?: number;
    nome?: string;
    email?: string;
    telefone?: string;
    clientes?: ICliente[];
}

export class Vendedor implements IVendedor {
    constructor(public id?: number, public nome?: string, public email?: string, public telefone?: string, public clientes?: ICliente[]) {}
}
