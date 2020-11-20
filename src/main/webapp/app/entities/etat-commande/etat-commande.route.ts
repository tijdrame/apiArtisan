import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEtatCommande, EtatCommande } from 'app/shared/model/etat-commande.model';
import { EtatCommandeService } from './etat-commande.service';
import { EtatCommandeComponent } from './etat-commande.component';
import { EtatCommandeDetailComponent } from './etat-commande-detail.component';
import { EtatCommandeUpdateComponent } from './etat-commande-update.component';

@Injectable({ providedIn: 'root' })
export class EtatCommandeResolve implements Resolve<IEtatCommande> {
  constructor(private service: EtatCommandeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtatCommande> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((etatCommande: HttpResponse<EtatCommande>) => {
          if (etatCommande.body) {
            return of(etatCommande.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtatCommande());
  }
}

export const etatCommandeRoute: Routes = [
  {
    path: '',
    component: EtatCommandeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'apiArtisanApp.etatCommande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EtatCommandeDetailComponent,
    resolve: {
      etatCommande: EtatCommandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.etatCommande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EtatCommandeUpdateComponent,
    resolve: {
      etatCommande: EtatCommandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.etatCommande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EtatCommandeUpdateComponent,
    resolve: {
      etatCommande: EtatCommandeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.etatCommande.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
