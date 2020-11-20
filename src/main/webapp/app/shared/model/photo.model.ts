import { IProduit } from 'app/shared/model/produit.model';

export interface IPhoto {
  id?: number;
  deleted?: boolean;
  photoContentType?: string;
  photo?: any;
  produit?: IProduit;
}

export class Photo implements IPhoto {
  constructor(
    public id?: number,
    public deleted?: boolean,
    public photoContentType?: string,
    public photo?: any,
    public produit?: IProduit
  ) {
    this.deleted = this.deleted || false;
  }
}
