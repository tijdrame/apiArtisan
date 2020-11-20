import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArtisan } from 'app/shared/model/artisan.model';

type EntityResponseType = HttpResponse<IArtisan>;
type EntityArrayResponseType = HttpResponse<IArtisan[]>;

@Injectable({ providedIn: 'root' })
export class ArtisanService {
  public resourceUrl = SERVER_API_URL + 'api/artisans';

  constructor(protected http: HttpClient) {}

  create(artisan: IArtisan): Observable<EntityResponseType> {
    return this.http.post<IArtisan>(this.resourceUrl, artisan, { observe: 'response' });
  }

  update(artisan: IArtisan): Observable<EntityResponseType> {
    return this.http.put<IArtisan>(this.resourceUrl, artisan, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArtisan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArtisan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
