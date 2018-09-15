import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEntrada } from 'app/shared/model/entrada.model';

type EntityResponseType = HttpResponse<IEntrada>;
type EntityArrayResponseType = HttpResponse<IEntrada[]>;

@Injectable({ providedIn: 'root' })
export class EntradaService {
    private resourceUrl = SERVER_API_URL + 'api/entradas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/entradas';

    constructor(private http: HttpClient) {}

    create(entrada: IEntrada): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(entrada);
        return this.http
            .post<IEntrada>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(entrada: IEntrada): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(entrada);
        return this.http
            .put<IEntrada>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEntrada>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEntrada[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEntrada[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(entrada: IEntrada): IEntrada {
        const copy: IEntrada = Object.assign({}, entrada, {
            data: entrada.data != null && entrada.data.isValid() ? entrada.data.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.data = res.body.data != null ? moment(res.body.data) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((entrada: IEntrada) => {
            entrada.data = entrada.data != null ? moment(entrada.data) : null;
        });
        return res;
    }
}
