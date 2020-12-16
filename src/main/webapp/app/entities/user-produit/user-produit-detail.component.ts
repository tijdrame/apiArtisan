import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserProduit } from 'app/shared/model/user-produit.model';

@Component({
  selector: 'jhi-user-produit-detail',
  templateUrl: './user-produit-detail.component.html',
})
export class UserProduitDetailComponent implements OnInit {
  userProduit: IUserProduit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userProduit }) => (this.userProduit = userProduit));
  }

  previousState(): void {
    window.history.back();
  }
}
