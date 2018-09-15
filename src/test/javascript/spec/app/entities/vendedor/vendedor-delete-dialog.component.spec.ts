/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterciacimentTestModule } from '../../../test.module';
import { VendedorDeleteDialogComponent } from 'app/entities/vendedor/vendedor-delete-dialog.component';
import { VendedorService } from 'app/entities/vendedor/vendedor.service';

describe('Component Tests', () => {
    describe('Vendedor Management Delete Component', () => {
        let comp: VendedorDeleteDialogComponent;
        let fixture: ComponentFixture<VendedorDeleteDialogComponent>;
        let service: VendedorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterciacimentTestModule],
                declarations: [VendedorDeleteDialogComponent]
            })
                .overrideTemplate(VendedorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VendedorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendedorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
