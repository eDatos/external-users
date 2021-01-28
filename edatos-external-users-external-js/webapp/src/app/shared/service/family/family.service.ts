import { Injectable } from '@angular/core';

import { ResponseUtils, createRequestOption } from 'arte-ng/src/lib/utils';
import { Family, InternationalString, LocalisedString } from '@app/shared/model';
import { ResponseWrapper } from 'arte-ng/src/lib/model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class FamilyService {
    private resourceUrl = 'api/families';

    constructor(private http: HttpClient) { }

    create(family: Family): Observable<Family> {
        this.preprocessInternationalString(family);
        return this.http.post(this.resourceUrl, family).pipe(map((res) => ResponseUtils.convert(res, Family)));
    }

    update(family: Family): Observable<Family> {
        this.preprocessInternationalString(family);
        return this.http.put(this.resourceUrl, family).pipe(map((res) => ResponseUtils.convert(res, Family)));
    }

    get(id: number): Observable<Family> {
        return this.http.get(`${this.resourceUrl}/${id}`).pipe(map((res) => ResponseUtils.convert(res, Family)));
    }

    find(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, { ...options, observe: 'response' }).pipe(map((res) => ResponseUtils.convertToResponseWrapper(res, Family)));
    }

    delete(id: number): Observable<Family> {
        return this.http.delete(`${this.resourceUrl}/${id}`).pipe(map((res) => ResponseUtils.convert(res, Family)));
    }

    publishInternally(id: number): Observable<Family> {
        return this.http.post(`${this.resourceUrl}/${id}/publish-internally`, id).pipe(map((res) => ResponseUtils.convert(res, Family)));
    }

    publishExternally(id: number): Observable<Family> {
        return this.http.post(`${this.resourceUrl}/${id}/publish-externally`, id).pipe(map((res) => ResponseUtils.convert(res, Family)));
    }

    // TODO EDATOS-3149 Eliminar esto cuando se cree el componente para gestionar los international string
    private preprocessInternationalString(family: Family) {
        let title = new InternationalString();
        let es = new LocalisedString();
        es.locale = "es";
        es.label = family.titleDefault;
        title.texts = [];
        title.texts.push(es);
        family.title = title;
    }
}
