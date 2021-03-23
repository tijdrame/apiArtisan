import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ApiArtisanTestModule } from '../../../test.module';
import { SpecialiteDetailComponent } from 'app/entities/specialite/specialite-detail.component';
import { Specialite } from 'app/shared/model/specialite.model';

describe('Component Tests', () => {
  describe('Specialite Management Detail Component', () => {
    let comp: SpecialiteDetailComponent;
    let fixture: ComponentFixture<SpecialiteDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ specialite: new Specialite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ApiArtisanTestModule],
        declarations: [SpecialiteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SpecialiteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpecialiteDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load specialite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.specialite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
