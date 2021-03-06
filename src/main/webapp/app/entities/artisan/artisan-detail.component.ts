import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IArtisan } from 'app/shared/model/artisan.model';

@Component({
  selector: 'jhi-artisan-detail',
  templateUrl: './artisan-detail.component.html',
})
export class ArtisanDetailComponent implements OnInit {
  artisan: IArtisan | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ artisan }) => (this.artisan = artisan));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
