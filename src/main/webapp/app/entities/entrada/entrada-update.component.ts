import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEntrada } from 'app/shared/model/entrada.model';
import { EntradaService } from './entrada.service';

@Component({
    selector: 'jhi-entrada-update',
    templateUrl: './entrada-update.component.html'
})
export class EntradaUpdateComponent implements OnInit {
    private _entrada: IEntrada;
    isSaving: boolean;
    data: string;

    constructor(private entradaService: EntradaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ entrada }) => {
            this.entrada = entrada;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.entrada.data = moment(this.data, DATE_TIME_FORMAT);
        if (this.entrada.id !== undefined) {
            this.subscribeToSaveResponse(this.entradaService.update(this.entrada));
        } else {
            this.subscribeToSaveResponse(this.entradaService.create(this.entrada));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEntrada>>) {
        result.subscribe((res: HttpResponse<IEntrada>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get entrada() {
        return this._entrada;
    }

    set entrada(entrada: IEntrada) {
        this._entrada = entrada;
        this.data = moment(entrada.data).format(DATE_TIME_FORMAT);
    }
}
