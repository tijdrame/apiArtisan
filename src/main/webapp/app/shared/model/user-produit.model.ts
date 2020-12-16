import { IUser } from 'app/core/user/user.model';
import { IProduit } from 'app/shared/model/produit.model';

export interface IUserProduit {
  id?: number;
  deleted?: boolean;
  user?: IUser;
  produit?: IProduit;
}

export class UserProduit implements IUserProduit {
  constructor(public id?: number, public deleted?: boolean, public user?: IUser, public produit?: IProduit) {
    this.deleted = this.deleted || false;
  }
}
