import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterciacimentEntradaModule } from './entrada/entrada.module';
import { JhipsterciacimentSaidaModule } from './saida/saida.module';
import { JhipsterciacimentClienteModule } from './cliente/cliente.module';
import { JhipsterciacimentVendedorModule } from './vendedor/vendedor.module';
import { JhipsterciacimentPedidoModule } from './pedido/pedido.module';
import { JhipsterciacimentItemModule } from './item/item.module';
import { JhipsterciacimentProdutoModule } from './produto/produto.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterciacimentEntradaModule,
        JhipsterciacimentSaidaModule,
        JhipsterciacimentClienteModule,
        JhipsterciacimentVendedorModule,
        JhipsterciacimentPedidoModule,
        JhipsterciacimentItemModule,
        JhipsterciacimentProdutoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterciacimentEntityModule {}
