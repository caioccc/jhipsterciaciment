import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IVendedor } from 'app/shared/model/vendedor.model';
import { VendedorService } from 'app/entities/vendedor';

@Component({
    selector: 'jhi-pedido-update',
    templateUrl: './pedido-update.component.html'
})
export class PedidoUpdateComponent implements OnInit {
    private _pedido: IPedido;
    isSaving: boolean;

    clientes: ICliente[];

    vendedors: IVendedor[];
    data: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private pedidoService: PedidoService,
        private clienteService: ClienteService,
        private vendedorService: VendedorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pedido }) => {
            this.pedido = pedido;
        });
        this.clienteService.query().subscribe(
            (res: HttpResponse<ICliente[]>) => {
                this.clientes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.vendedorService.query({ filter: 'pedido-is-null' }).subscribe(
            (res: HttpResponse<IVendedor[]>) => {
                if (!this.pedido.vendedorId) {
                    this.vendedors = res.body;
                } else {
                    this.vendedorService.find(this.pedido.vendedorId).subscribe(
                        (subRes: HttpResponse<IVendedor>) => {
                            this.vendedors = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.pedido.data = moment(this.data, DATE_TIME_FORMAT);
        if (this.pedido.id !== undefined) {
            this.subscribeToSaveResponse(this.pedidoService.update(this.pedido));
        } else {
            this.subscribeToSaveResponse(this.pedidoService.create(this.pedido));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>) {
        result.subscribe((res: HttpResponse<IPedido>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackClienteById(index: number, item: ICliente) {
        return item.id;
    }

    trackVendedorById(index: number, item: IVendedor) {
        return item.id;
    }
    get pedido() {
        return this._pedido;
    }

    set pedido(pedido: IPedido) {
        this._pedido = pedido;
        this.data = moment(pedido.data).format(DATE_TIME_FORMAT);
    }
}
