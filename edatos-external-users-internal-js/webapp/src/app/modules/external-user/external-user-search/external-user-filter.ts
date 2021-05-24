import { DatePipe } from '@angular/common';
import { Params } from '@angular/router';
import { Language } from '@app/core/model';
import { BaseEntityFilter, EntityFilter } from 'arte-ng/model';

export class ExternalUserFilter extends BaseEntityFilter implements EntityFilter {
    public fullname: string | null;
    public languages: Language[];
    public includeDeleted: boolean | null;
    public categories: string[];

    constructor(public datePipe?: DatePipe) {
        super(datePipe);
    }

    public processUrlParams(queryParams: Params) {
        this.includeDeleted = queryParams.hasOwnProperty('includeDeleted');
        this.fullname = queryParams.fullname;
        this.languages = queryParams.languages?.split(',');
        this.categories = queryParams.categories?.split(',');
    }

    public getCriterias() {
        const criterias: string[] = [];
        if (this.fullname) {
            criterias.push(`FULLNAME ILIKE '%${this.escapeSingleQuotes(this.fullname)}%'`);
        }
        if (this.languages.length > 0) {
            criterias.push(`LANGUAGE IN (${this.languages.join(',')})`);
        }
        if (!this.includeDeleted) {
            criterias.push(`DELETION_DATE IS_NULL`);
        }
        if (this.categories.length > 0) {
            criterias.push(`FAVORITES IN (${this.categories.join(',')})`);
        }
        return criterias;
    }

    protected registerParameters() {
        this.registerParam({
            paramName: 'fullname',
            updateFilterFromParam: (param) => (this.fullname = param),
            clearFilter: () => (this.fullname = null),
        });
        this.registerParam({
            paramName: 'languages',
            updateFilterFromParam: (param) => (this.languages = param),
            clearFilter: () => (this.languages = []),
        });
        this.registerParam({
            paramName: 'includeDeleted',
            updateFilterFromParam: (param) => {
                this.includeDeleted = param === 'true';
            },
            clearFilter: () => (this.includeDeleted = false),
        });
        this.registerParam({
            paramName: 'categories',
            updateFilterFromParam: (param) => (this.categories = param),
            clearFilter: () => (this.categories = []),
        });
    }
}
