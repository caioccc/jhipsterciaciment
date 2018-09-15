import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IItem } from 'app/shared/model/item.model';
import { ItemService } from './item.service';
import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from 'app/entities/pedido';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto';

@Component({
    selector: 'jhi-item-update',
    templateUrl: './item-update.component.html'
})
export class ItemUpdateComponent implements OnInit {
    private _item: IItem;
    isSaving: boolean;

    pedidos: IPedido[];

    produtos: IProduto[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private itemService: ItemService,
        private pedidoService: PedidoService,
        private produtoService: ProdutoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ item }) => {
            this.item = item;
        });
        this.pedidoService.query().subscribe(
            (res: HttpResponse<IPedido[]>) => {
                this.pedidos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.produtoService.query({ filter: 'item-is-null' }).subscribe(
            (res: HttpResponse<IProduto[]>) => {
                if (!this.item.produtoId) {
                    this.produtos = res.body;
                } else {
                    this.produtoService.find(this.item.produtoId).subscribe(
                        (subRes: HttpResponse<IProduto>) => {
                            this.produtos = [subRes.body].concat(res.body);
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
        if (this.item.id !== undefined) {
            this.subscribeToSaveResponse(this.itemService.update(this.item));
        } else {
            this.subscribeToSaveResponse(this.itemService.create(this.item));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IItem>>) {
        result.subscribe((res: HttpResponse<IItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPedidoById(index: number, item: IPedido) {
        return item.id;
    }

    trackProdutoById(index: number, item: IProduto) {
        return item.id;
    }
    get item() {
        return this._item;
    }

    set item(item: IItem) {
        this._item = item;
    }
}
