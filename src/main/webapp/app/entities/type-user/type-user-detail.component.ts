import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeUser } from 'app/shared/model/type-user.model';

@Component({
  selector: 'jhi-type-user-detail',
  templateUrl: './type-user-detail.component.html',
})
export class TypeUserDetailComponent implements OnInit {
  typeUser: ITypeUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeUser }) => (this.typeUser = typeUser));
  }

  previousState(): void {
    window.history.back();
  }
}
