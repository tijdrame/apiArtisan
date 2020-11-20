import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApiArtisanTestModule } from '../../../test.module';
import { TypeUserDetailComponent } from 'app/entities/type-user/type-user-detail.component';
import { TypeUser } from 'app/shared/model/type-user.model';

describe('Component Tests', () => {
  describe('TypeUser Management Detail Component', () => {
    let comp: TypeUserDetailComponent;
    let fixture: ComponentFixture<TypeUserDetailComponent>;
    const route = ({ data: of({ typeUser: new TypeUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ApiArtisanTestModule],
        declarations: [TypeUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TypeUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
