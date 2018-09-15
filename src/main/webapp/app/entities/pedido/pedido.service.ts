import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPedido } from 'app/shared/model/pedido.model';

type EntityResponseType = HttpResponse<IPedido>;
type EntityArrayResponseType = HttpResponse<IPedido[]>;

@Injectable({ providedIn: 'root' })
export class PedidoService {
    private resourceUrl = SERVER_API_URL + 'api/pedidos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/pedidos';

    constructor(private http: HttpClient) {}

    create(pedido: IPedido): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pedido);
        return this.http
            .post<IPedido>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pedido: IPedido): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pedido);
        return this.http
            .put<IPedido>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPedido>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPedido[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPedido[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(pedido: IPedido): IPedido {
        const copy: IPedido = Object.assign({}, pedido, {
            data: pedido.data != null && pedido.data.isValid() ? pedido.data.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.data = res.body.data != null ? moment(res.body.data) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((pedido: IPedido) => {
            pedido.data = pedido.data != null ? moment(pedido.data) : null;
        });
        return res;
    }
}
