import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApiArtisanSharedModule } from 'app/shared/shared.module';
import { ArtisanComponent } from './artisan.component';
import { ArtisanDetailComponent } from './artisan-detail.component';
import { ArtisanUpdateComponent } from './artisan-update.component';
import { ArtisanDeleteDialogComponent } from './artisan-delete-dialog.component';
import { artisanRoute } from './artisan.route';

@NgModule({
  imports: [ApiArtisanSharedModule, RouterModule.forChild(artisanRoute)],
  declarations: [ArtisanComponent, ArtisanDetailComponent, ArtisanUpdateComponent, ArtisanDeleteDialogComponent],
  entryComponents: [ArtisanDeleteDialogComponent],
})
export class ApiArtisanArtisanModule {}
