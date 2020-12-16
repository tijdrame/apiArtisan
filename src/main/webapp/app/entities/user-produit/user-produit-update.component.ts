import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserProduit, UserProduit } from 'app/shared/model/user-produit.model';
import { UserProduitService } from './user-produit.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';

type SelectableEntity = IUser | IProduit;

@Component({
  selector: 'jhi-user-produit-update',
  templateUrl: './user-produit-update.component.html',
})
export class UserProduitUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  produits: IProduit[] = [];

  editForm = this.fb.group({
    id: [],
    deleted: [],
    user: [],
    produit: [],
  });

  constructor(
    protected userProduitService: UserProduitService,
    protected userService: UserService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userProduit }) => {
      this.updateForm(userProduit);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));
    });
  }

  updateForm(userProduit: IUserProduit): void {
    this.editForm.patchValue({
      id: userProduit.id,
      deleted: userProduit.deleted,
      user: userProduit.user,
      produit: userProduit.produit,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userProduit = this.createFromForm();
    if (userProduit.id !== undefined) {
      this.subscribeToSaveResponse(this.userProduitService.update(userProduit));
    } else {
      this.subscribeToSaveResponse(this.userProduitService.create(userProduit));
    }
  }

  private createFromForm(): IUserProduit {
    return {
      ...new UserProduit(),
      id: this.editForm.get(['id'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      user: this.editForm.get(['user'])!.value,
      produit: this.editForm.get(['produit'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserProduit>>): void {
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
