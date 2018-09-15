import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVendedor } from 'app/shared/model/vendedor.model';

@Component({
    selector: 'jhi-vendedor-detail',
    templateUrl: './vendedor-detail.component.html'
})
export class VendedorDetailComponent implements OnInit {
    vendedor: IVendedor;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vendedor }) => {
            this.vendedor = vendedor;
        });
    }

    previousState() {
        window.history.back();
    }
}
