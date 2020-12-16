import { ISpecialite } from 'app/shared/model/specialite.model';
import { ILocalisation } from 'app/shared/model/localisation.model';
import { IUser } from 'app/core/user/user.model';

export interface IArtisan {
  id?: number;
  nom?: string;
  prenom?: string;
  login?: string;
  langKey?: string;
  photoContentType?: string;
  photo?: any;
  email?: string;
  telephone?: string;
  deleted?: boolean;
  specialite?: ISpecialite;
  localisation?: ILocalisation;
  user?: IUser;
}

export class Artisan implements IArtisan {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public login?: string,
    public langKey?: string,
    public photoContentType?: string,
    public photo?: any,
    public email?: string,
    public telephone?: string,
    public deleted?: boolean,
    public specialite?: ISpecialite,
    public localisation?: ILocalisation,
    public user?: IUser
  ) {
    this.deleted = this.deleted || false;
  }
}
