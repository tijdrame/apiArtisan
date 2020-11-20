import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ApiArtisanTestModule } from '../../../test.module';
import { ArtisanDetailComponent } from 'app/entities/artisan/artisan-detail.component';
import { Artisan } from 'app/shared/model/artisan.model';

describe('Component Tests', () => {
  describe('Artisan Management Detail Component', () => {
    let comp: ArtisanDetailComponent;
    let fixture: ComponentFixture<ArtisanDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ artisan: new Artisan(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ApiArtisanTestModule],
        declarations: [ArtisanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ArtisanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArtisanDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load artisan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.artisan).toEqual(jasmine.objectContaining({ id: 123 }));
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
