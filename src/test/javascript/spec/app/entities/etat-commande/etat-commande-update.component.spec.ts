import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ApiArtisanTestModule } from '../../../test.module';
import { EtatCommandeUpdateComponent } from 'app/entities/etat-commande/etat-commande-update.component';
import { EtatCommandeService } from 'app/entities/etat-commande/etat-commande.service';
import { EtatCommande } from 'app/shared/model/etat-commande.model';

describe('Component Tests', () => {
  describe('EtatCommande Management Update Component', () => {
    let comp: EtatCommandeUpdateComponent;
    let fixture: ComponentFixture<EtatCommandeUpdateComponent>;
    let service: EtatCommandeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ApiArtisanTestModule],
        declarations: [EtatCommandeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EtatCommandeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtatCommandeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtatCommandeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EtatCommande(123);
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
        const entity = new EtatCommande();
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
