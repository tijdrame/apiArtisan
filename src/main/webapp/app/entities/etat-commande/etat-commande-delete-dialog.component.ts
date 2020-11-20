import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtatCommande } from 'app/shared/model/etat-commande.model';
import { EtatCommandeService } from './etat-commande.service';

@Component({
  templateUrl: './etat-commande-delete-dialog.component.html',
})
export class EtatCommandeDeleteDialogComponent {
  etatCommande?: IEtatCommande;

  constructor(
    protected etatCommandeService: EtatCommandeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etatCommandeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('etatCommandeListModification');
      this.activeModal.close();
    });
  }
}
