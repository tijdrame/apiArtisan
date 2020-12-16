import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ApiArtisanTestModule } from '../../../test.module';
import { UserProduitUpdateComponent } from 'app/entities/user-produit/user-produit-update.component';
import { UserProduitService } from 'app/entities/user-produit/user-produit.service';
import { UserProduit } from 'app/shared/model/user-produit.model';

describe('Component Tests', () => {
  describe('UserProduit Management Update Component', () => {
    let comp: UserProduitUpdateComponent;
    let fixture: ComponentFixture<UserProduitUpdateComponent>;
    let service: UserProduitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ApiArtisanTestModule],
        declarations: [UserProduitUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserProduitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserProduitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserProduitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserProduit(123);
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
        const entity = new UserProduit();
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
