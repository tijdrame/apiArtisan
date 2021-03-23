import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISpecialite } from 'app/shared/model/specialite.model';

@Component({
  selector: 'jhi-specialite-detail',
  templateUrl: './specialite-detail.component.html',
})
export class SpecialiteDetailComponent implements OnInit {
  specialite: ISpecialite | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specialite }) => (this.specialite = specialite));
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
