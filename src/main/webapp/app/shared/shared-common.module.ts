import { NgModule } from '@angular/core';

import { JhipsterciacimentSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [JhipsterciacimentSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [JhipsterciacimentSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhipsterciacimentSharedCommonModule {}
