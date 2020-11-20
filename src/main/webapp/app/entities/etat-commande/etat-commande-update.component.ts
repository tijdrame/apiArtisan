import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtatCommande, EtatCommande } from 'app/shared/model/etat-commande.model';
import { EtatCommandeService } from './etat-commande.service';

@Component({
  selector: 'jhi-etat-commande-update',
  templateUrl: './etat-commande-update.component.html',
})
export class EtatCommandeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    deleted: [],
  });

  constructor(protected etatCommandeService: EtatCommandeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatCommande }) => {
      this.updateForm(etatCommande);
    });
  }

  updateForm(etatCommande: IEtatCommande): void {
    this.editForm.patchValue({
      id: etatCommande.id,
      libelle: etatCommande.libelle,
      code: etatCommande.code,
      deleted: etatCommande.deleted,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etatCommande = this.createFromForm();
    if (etatCommande.id !== undefined) {
      this.subscribeToSaveResponse(this.etatCommandeService.update(etatCommande));
    } else {
      this.subscribeToSaveResponse(this.etatCommandeService.create(etatCommande));
    }
  }

  private createFromForm(): IEtatCommande {
    return {
      ...new EtatCommande(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtatCommande>>): void {
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
}
