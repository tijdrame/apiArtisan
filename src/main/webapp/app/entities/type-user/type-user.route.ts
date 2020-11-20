import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeUser, TypeUser } from 'app/shared/model/type-user.model';
import { TypeUserService } from './type-user.service';
import { TypeUserComponent } from './type-user.component';
import { TypeUserDetailComponent } from './type-user-detail.component';
import { TypeUserUpdateComponent } from './type-user-update.component';

@Injectable({ providedIn: 'root' })
export class TypeUserResolve implements Resolve<ITypeUser> {
  constructor(private service: TypeUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeUser: HttpResponse<TypeUser>) => {
          if (typeUser.body) {
            return of(typeUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeUser());
  }
}

export const typeUserRoute: Routes = [
  {
    path: '',
    component: TypeUserComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'apiArtisanApp.typeUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeUserDetailComponent,
    resolve: {
      typeUser: TypeUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.typeUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeUserUpdateComponent,
    resolve: {
      typeUser: TypeUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.typeUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeUserUpdateComponent,
    resolve: {
      typeUser: TypeUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.typeUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
