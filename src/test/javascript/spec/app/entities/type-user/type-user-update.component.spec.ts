import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ApiArtisanTestModule } from '../../../test.module';
import { TypeUserUpdateComponent } from 'app/entities/type-user/type-user-update.component';
import { TypeUserService } from 'app/entities/type-user/type-user.service';
import { TypeUser } from 'app/shared/model/type-user.model';

describe('Component Tests', () => {
  describe('TypeUser Management Update Component', () => {
    let comp: TypeUserUpdateComponent;
    let fixture: ComponentFixture<TypeUserUpdateComponent>;
    let service: TypeUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ApiArtisanTestModule],
        declarations: [TypeUserUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TypeUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeUser(123);
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
        const entity = new TypeUser();
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
