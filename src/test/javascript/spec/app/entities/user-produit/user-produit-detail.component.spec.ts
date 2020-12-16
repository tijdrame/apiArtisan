import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApiArtisanTestModule } from '../../../test.module';
import { UserProduitDetailComponent } from 'app/entities/user-produit/user-produit-detail.component';
import { UserProduit } from 'app/shared/model/user-produit.model';

describe('Component Tests', () => {
  describe('UserProduit Management Detail Component', () => {
    let comp: UserProduitDetailComponent;
    let fixture: ComponentFixture<UserProduitDetailComponent>;
    const route = ({ data: of({ userProduit: new UserProduit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ApiArtisanTestModule],
        declarations: [UserProduitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserProduitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserProduitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userProduit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userProduit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
