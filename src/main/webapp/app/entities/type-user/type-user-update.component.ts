import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeUser, TypeUser } from 'app/shared/model/type-user.model';
import { TypeUserService } from './type-user.service';

@Component({
  selector: 'jhi-type-user-update',
  templateUrl: './type-user-update.component.html',
})
export class TypeUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    deleted: [],
  });

  constructor(protected typeUserService: TypeUserService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeUser }) => {
      this.updateForm(typeUser);
    });
  }

  updateForm(typeUser: ITypeUser): void {
    this.editForm.patchValue({
      id: typeUser.id,
      libelle: typeUser.libelle,
      code: typeUser.code,
      deleted: typeUser.deleted,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeUser = this.createFromForm();
    if (typeUser.id !== undefined) {
      this.subscribeToSaveResponse(this.typeUserService.update(typeUser));
    } else {
      this.subscribeToSaveResponse(this.typeUserService.create(typeUser));
    }
  }

  private createFromForm(): ITypeUser {
    return {
      ...new TypeUser(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeUser>>): void {
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
