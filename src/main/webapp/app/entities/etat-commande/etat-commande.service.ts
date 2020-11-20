import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEtatCommande } from 'app/shared/model/etat-commande.model';

type EntityResponseType = HttpResponse<IEtatCommande>;
type EntityArrayResponseType = HttpResponse<IEtatCommande[]>;

@Injectable({ providedIn: 'root' })
export class EtatCommandeService {
  public resourceUrl = SERVER_API_URL + 'api/etat-commandes';

  constructor(protected http: HttpClient) {}

  create(etatCommande: IEtatCommande): Observable<EntityResponseType> {
    return this.http.post<IEtatCommande>(this.resourceUrl, etatCommande, { observe: 'response' });
  }

  update(etatCommande: IEtatCommande): Observable<EntityResponseType> {
    return this.http.put<IEtatCommande>(this.resourceUrl, etatCommande, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtatCommande>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtatCommande[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
