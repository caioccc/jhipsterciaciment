import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Vendedor } from 'app/shared/model/vendedor.model';
import { VendedorService } from './vendedor.service';
import { VendedorComponent } from './vendedor.component';
import { VendedorDetailComponent } from './vendedor-detail.component';
import { VendedorUpdateComponent } from './vendedor-update.component';
import { VendedorDeletePopupComponent } from './vendedor-delete-dialog.component';
import { IVendedor } from 'app/shared/model/vendedor.model';

@Injectable({ providedIn: 'root' })
export class VendedorResolve implements Resolve<IVendedor> {
    constructor(private service: VendedorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((vendedor: HttpResponse<Vendedor>) => vendedor.body));
        }
        return of(new Vendedor());
    }
}

export const vendedorRoute: Routes = [
    {
        path: 'vendedor',
        component: VendedorComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Vendedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vendedor/:id/view',
        component: VendedorDetailComponent,
        resolve: {
            vendedor: VendedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vendedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vendedor/new',
        component: VendedorUpdateComponent,
        resolve: {
            vendedor: VendedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vendedors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vendedor/:id/edit',
        component: VendedorUpdateComponent,
        resolve: {
            vendedor: VendedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vendedors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vendedorPopupRoute: Routes = [
    {
        path: 'vendedor/:id/delete',
        component: VendedorDeletePopupComponent,
        resolve: {
            vendedor: VendedorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vendedors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
