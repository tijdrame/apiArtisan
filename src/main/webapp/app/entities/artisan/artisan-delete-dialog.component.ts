import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArtisan } from 'app/shared/model/artisan.model';
import { ArtisanService } from './artisan.service';

@Component({
  templateUrl: './artisan-delete-dialog.component.html',
})
export class ArtisanDeleteDialogComponent {
  artisan?: IArtisan;

  constructor(protected artisanService: ArtisanService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.artisanService.delete(id).subscribe(() => {
      this.eventManager.broadcast('artisanListModification');
      this.activeModal.close();
    });
  }
}
