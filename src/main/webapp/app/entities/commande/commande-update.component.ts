import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommande, Commande } from 'app/shared/model/commande.model';
import { CommandeService } from './commande.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { IEtatCommande } from 'app/shared/model/etat-commande.model';
import { EtatCommandeService } from 'app/entities/etat-commande/etat-commande.service';

type SelectableEntity = IProduit | IClient | IEtatCommande;

@Component({
  selector: 'jhi-commande-update',
  templateUrl: './commande-update.component.html',
})
export class CommandeUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];
  clients: IClient[] = [];
  etatcommandes: IEtatCommande[] = [];

  editForm = this.fb.group({
    id: [],
    dateCommande: [],
    quantite: [],
    deleted: [],
    produit: [],
    client: [],
    etatCommande: [],
  });

  constructor(
    protected commandeService: CommandeService,
    protected produitService: ProduitService,
    protected clientService: ClientService,
    protected etatCommandeService: EtatCommandeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commande }) => {
      if (!commande.id) {
        const today = moment().startOf('day');
        commande.dateCommande = today;
      }

      this.updateForm(commande);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));

      this.etatCommandeService.query().subscribe((res: HttpResponse<IEtatCommande[]>) => (this.etatcommandes = res.body || []));
    });
  }

  updateForm(commande: ICommande): void {
    this.editForm.patchValue({
      id: commande.id,
      dateCommande: commande.dateCommande ? commande.dateCommande.format(DATE_TIME_FORMAT) : null,
      quantite: commande.quantite,
      deleted: commande.deleted,
      produit: commande.produit,
      client: commande.client,
      etatCommande: commande.etatCommande,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commande = this.createFromForm();
    if (commande.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeService.update(commande));
    } else {
      this.subscribeToSaveResponse(this.commandeService.create(commande));
    }
  }

  private createFromForm(): ICommande {
    return {
      ...new Commande(),
      id: this.editForm.get(['id'])!.value,
      dateCommande: this.editForm.get(['dateCommande'])!.value
        ? moment(this.editForm.get(['dateCommande'])!.value, DATE_TIME_FORMAT)
        : undefined,
      quantite: this.editForm.get(['quantite'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      client: this.editForm.get(['client'])!.value,
      etatCommande: this.editForm.get(['etatCommande'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommande>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
