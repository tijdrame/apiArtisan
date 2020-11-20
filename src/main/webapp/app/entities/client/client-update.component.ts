import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ILocalisation } from 'app/shared/model/localisation.model';
import { LocalisationService } from 'app/entities/localisation/localisation.service';

type SelectableEntity = IUser | ILocalisation;

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  localisations: ILocalisation[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    login: [null, [Validators.required]],
    password: [],
    langKey: [],
    photo: [],
    photoContentType: [],
    email: [null, []],
    telephone: [],
    deleted: [],
    users: [],
    localisation: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected clientService: ClientService,
    protected userService: UserService,
    protected localisationService: LocalisationService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.localisationService.query().subscribe((res: HttpResponse<ILocalisation[]>) => (this.localisations = res.body || []));
    });
  }

  updateForm(client: IClient): void {
    this.editForm.patchValue({
      id: client.id,
      nom: client.nom,
      prenom: client.prenom,
      login: client.login,
      password: client.password,
      langKey: client.langKey,
      photo: client.photo,
      photoContentType: client.photoContentType,
      email: client.email,
      telephone: client.telephone,
      deleted: client.deleted,
      users: client.users,
      localisation: client.localisation,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('apiArtisanApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.createFromForm();
    if (client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  private createFromForm(): IClient {
    return {
      ...new Client(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      login: this.editForm.get(['login'])!.value,
      password: this.editForm.get(['password'])!.value,
      langKey: this.editForm.get(['langKey'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      email: this.editForm.get(['email'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      users: this.editForm.get(['users'])!.value,
      localisation: this.editForm.get(['localisation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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
