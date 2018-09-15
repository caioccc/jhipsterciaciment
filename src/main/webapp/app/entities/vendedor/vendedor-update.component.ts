import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVendedor } from 'app/shared/model/vendedor.model';
import { VendedorService } from './vendedor.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
    selector: 'jhi-vendedor-update',
    templateUrl: './vendedor-update.component.html'
})
export class VendedorUpdateComponent implements OnInit {
    private _vendedor: IVendedor;
    isSaving: boolean;

    clientes: ICliente[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private vendedorService: VendedorService,
        private clienteService: ClienteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vendedor }) => {
            this.vendedor = vendedor;
        });
        this.clienteService.query().subscribe(
            (res: HttpResponse<ICliente[]>) => {
                this.clientes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.vendedor.id !== undefined) {
            this.subscribeToSaveResponse(this.vendedorService.update(this.vendedor));
        } else {
            this.subscribeToSaveResponse(this.vendedorService.create(this.vendedor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVendedor>>) {
        result.subscribe((res: HttpResponse<IVendedor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get vendedor() {
        return this._vendedor;
    }

    set vendedor(vendedor: IVendedor) {
        this._vendedor = vendedor;
    }
}
