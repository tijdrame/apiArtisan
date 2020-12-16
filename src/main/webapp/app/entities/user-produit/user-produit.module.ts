import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApiArtisanSharedModule } from 'app/shared/shared.module';
import { UserProduitComponent } from './user-produit.component';
import { UserProduitDetailComponent } from './user-produit-detail.component';
import { UserProduitUpdateComponent } from './user-produit-update.component';
import { UserProduitDeleteDialogComponent } from './user-produit-delete-dialog.component';
import { userProduitRoute } from './user-produit.route';

@NgModule({
  imports: [ApiArtisanSharedModule, RouterModule.forChild(userProduitRoute)],
  declarations: [UserProduitComponent, UserProduitDetailComponent, UserProduitUpdateComponent, UserProduitDeleteDialogComponent],
  entryComponents: [UserProduitDeleteDialogComponent],
})
export class ApiArtisanUserProduitModule {}
