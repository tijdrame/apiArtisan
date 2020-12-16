import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'specialite',
        loadChildren: () => import('./specialite/specialite.module').then(m => m.ApiArtisanSpecialiteModule),
      },
      {
        path: 'etat-commande',
        loadChildren: () => import('./etat-commande/etat-commande.module').then(m => m.ApiArtisanEtatCommandeModule),
      },
      {
        path: 'photo',
        loadChildren: () => import('./photo/photo.module').then(m => m.ApiArtisanPhotoModule),
      },
      {
        path: 'localisation',
        loadChildren: () => import('./localisation/localisation.module').then(m => m.ApiArtisanLocalisationModule),
      },
      {
        path: 'type-user',
        loadChildren: () => import('./type-user/type-user.module').then(m => m.ApiArtisanTypeUserModule),
      },
      {
        path: 'artisan',
        loadChildren: () => import('./artisan/artisan.module').then(m => m.ApiArtisanArtisanModule),
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.ApiArtisanClientModule),
      },
      {
        path: 'produit',
        loadChildren: () => import('./produit/produit.module').then(m => m.ApiArtisanProduitModule),
      },
      {
        path: 'commande',
        loadChildren: () => import('./commande/commande.module').then(m => m.ApiArtisanCommandeModule),
      },
      {
        path: 'user-produit',
        loadChildren: () => import('./user-produit/user-produit.module').then(m => m.ApiArtisanUserProduitModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ApiArtisanEntityModule {}
