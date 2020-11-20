import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApiArtisanSharedModule } from 'app/shared/shared.module';
import { PhotoComponent } from './photo.component';
import { PhotoDetailComponent } from './photo-detail.component';
import { PhotoUpdateComponent } from './photo-update.component';
import { PhotoDeleteDialogComponent } from './photo-delete-dialog.component';
import { photoRoute } from './photo.route';

@NgModule({
  imports: [ApiArtisanSharedModule, RouterModule.forChild(photoRoute)],
  declarations: [PhotoComponent, PhotoDetailComponent, PhotoUpdateComponent, PhotoDeleteDialogComponent],
  entryComponents: [PhotoDeleteDialogComponent],
})
export class ApiArtisanPhotoModule {}
