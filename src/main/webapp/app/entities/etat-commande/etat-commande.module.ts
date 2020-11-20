import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApiArtisanSharedModule } from 'app/shared/shared.module';
import { EtatCommandeComponent } from './etat-commande.component';
import { EtatCommandeDetailComponent } from './etat-commande-detail.component';
import { EtatCommandeUpdateComponent } from './etat-commande-update.component';
import { EtatCommandeDeleteDialogComponent } from './etat-commande-delete-dialog.component';
import { etatCommandeRoute } from './etat-commande.route';

@NgModule({
  imports: [ApiArtisanSharedModule, RouterModule.forChild(etatCommandeRoute)],
  declarations: [EtatCommandeComponent, EtatCommandeDetailComponent, EtatCommandeUpdateComponent, EtatCommandeDeleteDialogComponent],
  entryComponents: [EtatCommandeDeleteDialogComponent],
})
export class ApiArtisanEtatCommandeModule {}
