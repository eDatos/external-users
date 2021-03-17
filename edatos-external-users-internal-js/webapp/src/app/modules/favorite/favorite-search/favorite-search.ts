import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';
import { BaseEntityFilter, EntityFilter } from 'arte-ng/model';

@Injectable()
export class FavoriteFilter extends BaseEntityFilter implements EntityFilter {
    public name: string;
    public login: string;

    constructor(public datePipe?: DatePipe) {
        super(datePipe);
    }

    protected registerParameters() {
        this.registerParam({
            paramName: 'name',
            updateFilterFromParam: (param) => (this.name = param),
            clearFilter: () => (this.name = null),
        });
        this.registerParam({
            paramName: 'login',
            updateFilterFromParam: (param) => (this.login = param),
            clearFilter: () => (this.login = null),
        });
    }

    public getCriterias() {
        const criterias = [];
        if (this.name) {
            criterias.push(`NAME ILIKE '%${this.escapeSingleQuotes(this.name)}%'`);
        }
        if (this.login) {
            criterias.push(`LOGIN ILIKE '%${this.escapeSingleQuotes(this.login)}%'`);
        }
        return criterias;
    }

    public fromCamelCaseToSnakeCase(param: string): string {
        return param.replace(/([A-Z])/g, '_$1').toLowerCase();
    }
}
