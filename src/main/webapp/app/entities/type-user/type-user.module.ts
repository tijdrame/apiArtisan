import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApiArtisanSharedModule } from 'app/shared/shared.module';
import { TypeUserComponent } from './type-user.component';
import { TypeUserDetailComponent } from './type-user-detail.component';
import { TypeUserUpdateComponent } from './type-user-update.component';
import { TypeUserDeleteDialogComponent } from './type-user-delete-dialog.component';
import { typeUserRoute } from './type-user.route';

@NgModule({
  imports: [ApiArtisanSharedModule, RouterModule.forChild(typeUserRoute)],
  declarations: [TypeUserComponent, TypeUserDetailComponent, TypeUserUpdateComponent, TypeUserDeleteDialogComponent],
  entryComponents: [TypeUserDeleteDialogComponent],
})
export class ApiArtisanTypeUserModule {}
