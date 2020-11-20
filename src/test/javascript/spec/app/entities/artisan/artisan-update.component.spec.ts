import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ApiArtisanTestModule } from '../../../test.module';
import { ArtisanUpdateComponent } from 'app/entities/artisan/artisan-update.component';
import { ArtisanService } from 'app/entities/artisan/artisan.service';
import { Artisan } from 'app/shared/model/artisan.model';

describe('Component Tests', () => {
  describe('Artisan Management Update Component', () => {
    let comp: ArtisanUpdateComponent;
    let fixture: ComponentFixture<ArtisanUpdateComponent>;
    let service: ArtisanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ApiArtisanTestModule],
        declarations: [ArtisanUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ArtisanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArtisanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArtisanService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Artisan(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Artisan();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
