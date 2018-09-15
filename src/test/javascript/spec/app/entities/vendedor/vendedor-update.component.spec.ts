/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterciacimentTestModule } from '../../../test.module';
import { VendedorUpdateComponent } from 'app/entities/vendedor/vendedor-update.component';
import { VendedorService } from 'app/entities/vendedor/vendedor.service';
import { Vendedor } from 'app/shared/model/vendedor.model';

describe('Component Tests', () => {
    describe('Vendedor Management Update Component', () => {
        let comp: VendedorUpdateComponent;
        let fixture: ComponentFixture<VendedorUpdateComponent>;
        let service: VendedorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterciacimentTestModule],
                declarations: [VendedorUpdateComponent]
            })
                .overrideTemplate(VendedorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VendedorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendedorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Vendedor(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vendedor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Vendedor();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vendedor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
