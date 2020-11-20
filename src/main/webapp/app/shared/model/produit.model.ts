import { IArtisan } from 'app/shared/model/artisan.model';
import { IPhoto } from 'app/shared/model/photo.model';

export interface IProduit {
  id?: number;
  libelle?: string;
  code?: string;
  prix?: number;
  deleted?: boolean;
  description?: string;
  genre?: string;
  artisan?: IArtisan;
  photos?: IPhoto[];
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public libelle?: string,
    public code?: string,
    public prix?: number,
    public deleted?: boolean,
    public description?: string,
    public genre?: string,
    public artisan?: IArtisan,
    public photos?: IPhoto[]
  ) {
    this.deleted = this.deleted || false;
  }
}
