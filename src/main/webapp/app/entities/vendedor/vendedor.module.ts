import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterciacimentSharedModule } from 'app/shared';
import {
    VendedorComponent,
    VendedorDetailComponent,
    VendedorUpdateComponent,
    VendedorDeletePopupComponent,
    VendedorDeleteDialogComponent,
    vendedorRoute,
    vendedorPopupRoute
} from './';

const ENTITY_STATES = [...vendedorRoute, ...vendedorPopupRoute];

@NgModule({
    imports: [JhipsterciacimentSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VendedorComponent,
        VendedorDetailComponent,
        VendedorUpdateComponent,
        VendedorDeleteDialogComponent,
        VendedorDeletePopupComponent
    ],
    entryComponents: [VendedorComponent, VendedorUpdateComponent, VendedorDeleteDialogComponent, VendedorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterciacimentVendedorModule {}
