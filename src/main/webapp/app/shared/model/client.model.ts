import { IUser } from 'app/core/user/user.model';
import { ILocalisation } from 'app/shared/model/localisation.model';

export interface IClient {
  id?: number;
  nom?: string;
  prenom?: string;
  login?: string;
  password?: string;
  langKey?: string;
  photoContentType?: string;
  photo?: any;
  email?: string;
  telephone?: string;
  deleted?: boolean;
  users?: IUser;
  localisation?: ILocalisation;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public login?: string,
    public password?: string,
    public langKey?: string,
    public photoContentType?: string,
    public photo?: any,
    public email?: string,
    public telephone?: string,
    public deleted?: boolean,
    public users?: IUser,
    public localisation?: ILocalisation
  ) {
    this.deleted = this.deleted || false;
  }
}
