/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterciacimentTestModule } from '../../../test.module';
import { VendedorDetailComponent } from 'app/entities/vendedor/vendedor-detail.component';
import { Vendedor } from 'app/shared/model/vendedor.model';

describe('Component Tests', () => {
    describe('Vendedor Management Detail Component', () => {
        let comp: VendedorDetailComponent;
        let fixture: ComponentFixture<VendedorDetailComponent>;
        const route = ({ data: of({ vendedor: new Vendedor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterciacimentTestModule],
                declarations: [VendedorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VendedorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VendedorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.vendedor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
