import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatCommande } from 'app/shared/model/etat-commande.model';

@Component({
  selector: 'jhi-etat-commande-detail',
  templateUrl: './etat-commande-detail.component.html',
})
export class EtatCommandeDetailComponent implements OnInit {
  etatCommande: IEtatCommande | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatCommande }) => (this.etatCommande = etatCommande));
  }

  previousState(): void {
    window.history.back();
  }
}
