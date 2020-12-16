import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserProduit, UserProduit } from 'app/shared/model/user-produit.model';
import { UserProduitService } from './user-produit.service';
import { UserProduitComponent } from './user-produit.component';
import { UserProduitDetailComponent } from './user-produit-detail.component';
import { UserProduitUpdateComponent } from './user-produit-update.component';

@Injectable({ providedIn: 'root' })
export class UserProduitResolve implements Resolve<IUserProduit> {
  constructor(private service: UserProduitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserProduit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userProduit: HttpResponse<UserProduit>) => {
          if (userProduit.body) {
            return of(userProduit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserProduit());
  }
}

export const userProduitRoute: Routes = [
  {
    path: '',
    component: UserProduitComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'apiArtisanApp.userProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserProduitDetailComponent,
    resolve: {
      userProduit: UserProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.userProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserProduitUpdateComponent,
    resolve: {
      userProduit: UserProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.userProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserProduitUpdateComponent,
    resolve: {
      userProduit: UserProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.userProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
