import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeUser } from 'app/shared/model/type-user.model';
import { TypeUserService } from './type-user.service';

@Component({
  templateUrl: './type-user-delete-dialog.component.html',
})
export class TypeUserDeleteDialogComponent {
  typeUser?: ITypeUser;

  constructor(protected typeUserService: TypeUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeUserListModification');
      this.activeModal.close();
    });
  }
}
