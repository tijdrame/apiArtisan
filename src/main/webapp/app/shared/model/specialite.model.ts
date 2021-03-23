export interface ISpecialite {
  id?: number;
  libelle?: string;
  code?: string;
  havingGenre?: boolean;
  deleted?: boolean;
  avatarContentType?: string;
  avatar?: any;
}

export class Specialite implements ISpecialite {
  constructor(
    public id?: number,
    public libelle?: string,
    public code?: string,
    public havingGenre?: boolean,
    public deleted?: boolean,
    public avatarContentType?: string,
    public avatar?: any
  ) {
    this.havingGenre = this.havingGenre || false;
    this.deleted = this.deleted || false;
  }
}
