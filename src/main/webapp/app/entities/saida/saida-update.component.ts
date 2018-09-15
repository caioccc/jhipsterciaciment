import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISaida } from 'app/shared/model/saida.model';
import { SaidaService } from './saida.service';

@Component({
    selector: 'jhi-saida-update',
    templateUrl: './saida-update.component.html'
})
export class SaidaUpdateComponent implements OnInit {
    private _saida: ISaida;
    isSaving: boolean;
    data: string;

    constructor(private saidaService: SaidaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ saida }) => {
            this.saida = saida;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.saida.data = moment(this.data, DATE_TIME_FORMAT);
        if (this.saida.id !== undefined) {
            this.subscribeToSaveResponse(this.saidaService.update(this.saida));
        } else {
            this.subscribeToSaveResponse(this.saidaService.create(this.saida));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISaida>>) {
        result.subscribe((res: HttpResponse<ISaida>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get saida() {
        return this._saida;
    }

    set saida(saida: ISaida) {
        this._saida = saida;
        this.data = moment(saida.data).format(DATE_TIME_FORMAT);
    }
}
