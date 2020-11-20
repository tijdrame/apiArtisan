export interface ITypeUser {
  id?: number;
  libelle?: string;
  code?: string;
  deleted?: boolean;
}

export class TypeUser implements ITypeUser {
  constructor(public id?: number, public libelle?: string, public code?: string, public deleted?: boolean) {
    this.deleted = this.deleted || false;
  }
}
