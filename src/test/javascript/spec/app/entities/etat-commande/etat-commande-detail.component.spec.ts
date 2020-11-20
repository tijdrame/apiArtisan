import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApiArtisanTestModule } from '../../../test.module';
import { EtatCommandeDetailComponent } from 'app/entities/etat-commande/etat-commande-detail.component';
import { EtatCommande } from 'app/shared/model/etat-commande.model';

describe('Component Tests', () => {
  describe('EtatCommande Management Detail Component', () => {
    let comp: EtatCommandeDetailComponent;
    let fixture: ComponentFixture<EtatCommandeDetailComponent>;
    const route = ({ data: of({ etatCommande: new EtatCommande(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ApiArtisanTestModule],
        declarations: [EtatCommandeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EtatCommandeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtatCommandeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etatCommande on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etatCommande).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
