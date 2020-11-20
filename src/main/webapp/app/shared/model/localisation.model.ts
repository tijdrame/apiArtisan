export interface ILocalisation {
  id?: number;
  latitude?: number;
  longitude?: number;
  adresse?: string;
  deleted?: boolean;
}

export class Localisation implements ILocalisation {
  constructor(public id?: number, public latitude?: number, public longitude?: number, public adresse?: string, public deleted?: boolean) {
    this.deleted = this.deleted || false;
  }
}
