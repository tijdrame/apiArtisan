import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserProduit } from 'app/shared/model/user-produit.model';

type EntityResponseType = HttpResponse<IUserProduit>;
type EntityArrayResponseType = HttpResponse<IUserProduit[]>;

@Injectable({ providedIn: 'root' })
export class UserProduitService {
  public resourceUrl = SERVER_API_URL + 'api/user-produits';

  constructor(protected http: HttpClient) {}

  create(userProduit: IUserProduit): Observable<EntityResponseType> {
    return this.http.post<IUserProduit>(this.resourceUrl, userProduit, { observe: 'response' });
  }

  update(userProduit: IUserProduit): Observable<EntityResponseType> {
    return this.http.put<IUserProduit>(this.resourceUrl, userProduit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserProduit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserProduit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
