import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApiArtisanSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [ApiArtisanSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class ApiArtisanHomeModule {}
