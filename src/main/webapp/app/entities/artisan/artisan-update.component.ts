import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IArtisan, Artisan } from 'app/shared/model/artisan.model';
import { ArtisanService } from './artisan.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ISpecialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from 'app/entities/specialite/specialite.service';
import { ILocalisation } from 'app/shared/model/localisation.model';
import { LocalisationService } from 'app/entities/localisation/localisation.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = ISpecialite | ILocalisation | IUser;

@Component({
  selector: 'jhi-artisan-update',
  templateUrl: './artisan-update.component.html',
})
export class ArtisanUpdateComponent implements OnInit {
  isSaving = false;
  specialites: ISpecialite[] = [];
  localisations: ILocalisation[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    login: [null, []],
    langKey: [],
    photo: [],
    photoContentType: [],
    email: [null, []],
    telephone: [null, []],
    deleted: [],
    specialite: [],
    localisation: [],
    user: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected artisanService: ArtisanService,
    protected specialiteService: SpecialiteService,
    protected localisationService: LocalisationService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ artisan }) => {
      this.updateForm(artisan);

      this.specialiteService.query().subscribe((res: HttpResponse<ISpecialite[]>) => (this.specialites = res.body || []));

      this.localisationService.query().subscribe((res: HttpResponse<ILocalisation[]>) => (this.localisations = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(artisan: IArtisan): void {
    this.editForm.patchValue({
      id: artisan.id,
      nom: artisan.nom,
      prenom: artisan.prenom,
      login: artisan.login,
      langKey: artisan.langKey,
      photo: artisan.photo,
      photoContentType: artisan.photoContentType,
      email: artisan.email,
      telephone: artisan.telephone,
      deleted: artisan.deleted,
      specialite: artisan.specialite,
      localisation: artisan.localisation,
      user: artisan.user,
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
    const artisan = this.createFromForm();
    if (artisan.id !== undefined) {
      this.subscribeToSaveResponse(this.artisanService.update(artisan));
    } else {
      this.subscribeToSaveResponse(this.artisanService.create(artisan));
    }
  }

  private createFromForm(): IArtisan {
    return {
      ...new Artisan(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      login: this.editForm.get(['login'])!.value,
      langKey: this.editForm.get(['langKey'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      email: this.editForm.get(['email'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      specialite: this.editForm.get(['specialite'])!.value,
      localisation: this.editForm.get(['localisation'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArtisan>>): void {
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
