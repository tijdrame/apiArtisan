import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserProduit } from 'app/shared/model/user-produit.model';
import { UserProduitService } from './user-produit.service';

@Component({
  templateUrl: './user-produit-delete-dialog.component.html',
})
export class UserProduitDeleteDialogComponent {
  userProduit?: IUserProduit;

  constructor(
    protected userProduitService: UserProduitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userProduitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userProduitListModification');
      this.activeModal.close();
    });
  }
}
