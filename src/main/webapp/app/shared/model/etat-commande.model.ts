export interface IEtatCommande {
  id?: number;
  libelle?: string;
  code?: string;
  deleted?: boolean;
}

export class EtatCommande implements IEtatCommande {
  constructor(public id?: number, public libelle?: string, public code?: string, public deleted?: boolean) {
    this.deleted = this.deleted || false;
  }
}
