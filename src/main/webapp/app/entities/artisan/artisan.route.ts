import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArtisan, Artisan } from 'app/shared/model/artisan.model';
import { ArtisanService } from './artisan.service';
import { ArtisanComponent } from './artisan.component';
import { ArtisanDetailComponent } from './artisan-detail.component';
import { ArtisanUpdateComponent } from './artisan-update.component';

@Injectable({ providedIn: 'root' })
export class ArtisanResolve implements Resolve<IArtisan> {
  constructor(private service: ArtisanService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArtisan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((artisan: HttpResponse<Artisan>) => {
          if (artisan.body) {
            return of(artisan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Artisan());
  }
}

export const artisanRoute: Routes = [
  {
    path: '',
    component: ArtisanComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'apiArtisanApp.artisan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArtisanDetailComponent,
    resolve: {
      artisan: ArtisanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.artisan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArtisanUpdateComponent,
    resolve: {
      artisan: ArtisanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.artisan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArtisanUpdateComponent,
    resolve: {
      artisan: ArtisanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'apiArtisanApp.artisan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
