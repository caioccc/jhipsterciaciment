import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Saida } from 'app/shared/model/saida.model';
import { SaidaService } from './saida.service';
import { SaidaComponent } from './saida.component';
import { SaidaDetailComponent } from './saida-detail.component';
import { SaidaUpdateComponent } from './saida-update.component';
import { SaidaDeletePopupComponent } from './saida-delete-dialog.component';
import { ISaida } from 'app/shared/model/saida.model';

@Injectable({ providedIn: 'root' })
export class SaidaResolve implements Resolve<ISaida> {
    constructor(private service: SaidaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((saida: HttpResponse<Saida>) => saida.body));
        }
        return of(new Saida());
    }
}

export const saidaRoute: Routes = [
    {
        path: 'saida',
        component: SaidaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Saidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'saida/:id/view',
        component: SaidaDetailComponent,
        resolve: {
            saida: SaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Saidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'saida/new',
        component: SaidaUpdateComponent,
        resolve: {
            saida: SaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Saidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'saida/:id/edit',
        component: SaidaUpdateComponent,
        resolve: {
            saida: SaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Saidas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saidaPopupRoute: Routes = [
    {
        path: 'saida/:id/delete',
        component: SaidaDeletePopupComponent,
        resolve: {
            saida: SaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Saidas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
