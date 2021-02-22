import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';
import { BaseEntityFilter, EntityFilter } from 'arte-ng/model';

@Injectable()
export class FilterFilter extends BaseEntityFilter implements EntityFilter {
    public name: string;
    public login: string;
    public lastAccessDate: Date;

    constructor(public datePipe?: DatePipe) {
        super(datePipe);
    }

    protected registerParameters() {
        this.registerParam({
            paramName: 'name',
            updateFilterFromParam: (param) => (this.name = param),
            clearFilter: () => (this.name = null)
        });
        this.registerParam({
            paramName: 'login',
            updateFilterFromParam: (param) => (this.login = param),
            clearFilter: () => (this.login = null)
        });
        this.registerParam({
            paramName: 'lastAccessDate',
            updateFilterFromParam: (param) => (this.lastAccessDate = param),
            clearFilter: () => (this.lastAccessDate = null)
        });
    }

    getCriterias() {
        const criterias = [];
        if (this.name) {
            criterias.push(`NAME ILIKE '%${this.escapeSingleQuotes(this.name)}%'`);
        }
        if (this.login) {
            criterias.push(`LOGIN ILIKE '%${this.escapeSingleQuotes(this.login)}%'`);
        }
        if (this.lastAccessDate) {
            criterias.push(`LAST_ACCESS_DATE = '%${this.escapeSingleQuotes(this.lastAccessDate.toString())}%'`);
        }
        return criterias;
    }

    fromCamelCaseToSnakeCase(param: string): string {
        // TODO(EDATOS-3280): Standard casing for query parameters?
        return param.replace(/([A-Z])/g, '_$1').toLowerCase();
    }
}
