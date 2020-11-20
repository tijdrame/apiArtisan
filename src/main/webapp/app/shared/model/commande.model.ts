import { Moment } from 'moment';
import { IProduit } from 'app/shared/model/produit.model';
import { IClient } from 'app/shared/model/client.model';
import { IEtatCommande } from 'app/shared/model/etat-commande.model';

export interface ICommande {
  id?: number;
  dateCommande?: Moment;
  quantite?: number;
  deleted?: boolean;
  produit?: IProduit;
  client?: IClient;
  etatCommande?: IEtatCommande;
}

export class Commande implements ICommande {
  constructor(
    public id?: number,
    public dateCommande?: Moment,
    public quantite?: number,
    public deleted?: boolean,
    public produit?: IProduit,
    public client?: IClient,
    public etatCommande?: IEtatCommande
  ) {
    this.deleted = this.deleted || false;
  }
}
