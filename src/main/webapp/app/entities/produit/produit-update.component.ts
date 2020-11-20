import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProduit, Produit } from 'app/shared/model/produit.model';
import { ProduitService } from './produit.service';
import { IArtisan } from 'app/shared/model/artisan.model';
import { ArtisanService } from 'app/entities/artisan/artisan.service';

@Component({
  selector: 'jhi-produit-update',
  templateUrl: './produit-update.component.html',
})
export class ProduitUpdateComponent implements OnInit {
  isSaving = false;
  artisans: IArtisan[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    prix: [null, [Validators.required]],
    deleted: [],
    description: [],
    genre: [],
    artisan: [],
  });

  constructor(
    protected produitService: ProduitService,
    protected artisanService: ArtisanService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produit }) => {
      this.updateForm(produit);

      this.artisanService.query().subscribe((res: HttpResponse<IArtisan[]>) => (this.artisans = res.body || []));
    });
  }

  updateForm(produit: IProduit): void {
    this.editForm.patchValue({
      id: produit.id,
      libelle: produit.libelle,
      code: produit.code,
      prix: produit.prix,
      deleted: produit.deleted,
      description: produit.description,
      genre: produit.genre,
      artisan: produit.artisan,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produit = this.createFromForm();
    if (produit.id !== undefined) {
      this.subscribeToSaveResponse(this.produitService.update(produit));
    } else {
      this.subscribeToSaveResponse(this.produitService.create(produit));
    }
  }

  private createFromForm(): IProduit {
    return {
      ...new Produit(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      description: this.editForm.get(['description'])!.value,
      genre: this.editForm.get(['genre'])!.value,
      artisan: this.editForm.get(['artisan'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduit>>): void {
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

  trackById(index: number, item: IArtisan): any {
    return item.id;
  }
}
