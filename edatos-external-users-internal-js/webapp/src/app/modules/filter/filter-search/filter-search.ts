import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';
import { BaseEntityFilter, EntityFilter } from 'arte-ng/model';

@Injectable()
export class FilterFilter extends BaseEntityFilter implements EntityFilter {
    public name: string;
    public user: string;
    public includeFromDeletedUsers = false;
    public lastAccessDate: Date;

    constructor(public datePipe?: DatePipe) {
        super(datePipe);
    }

    public getCriterias() {
        const criterias = [];
        if (this.name) {
            criterias.push(`NAME ILIKE '%${this.escapeSingleQuotes(this.name)}%'`);
        }
        if (this.user) {
            criterias.push(`USER ILIKE '%${this.escapeSingleQuotes(this.user)}%'`);
        }
        if (this.includeFromDeletedUsers) {
            criterias.push(`USER_DELETION_DATE IS_NULL`);
        }
        if (this.lastAccessDate) {
            criterias.push(`LAST_ACCESS_DATE EQ '%${this.escapeSingleQuotes(this.lastAccessDate.toString())}%'`);
        }
        return criterias;
    }

    protected registerParameters() {
        this.registerParam({
            paramName: 'name',
            updateFilterFromParam: (param) => (this.name = param),
            clearFilter: () => (this.name = null),
        });
        this.registerParam({
            paramName: 'user',
            updateFilterFromParam: (param) => (this.user = param),
            clearFilter: () => (this.user = null),
        });
        this.registerParam({
            paramName: 'includeFromDeletedUsers',
            updateFilterFromParam: (param) => {
                this.includeFromDeletedUsers = param === 'true';
            },
            clearFilter: () => (this.includeFromDeletedUsers = false),
        });
        this.registerParam({
            paramName: 'lastAccessDate',
            updateFilterFromParam: (param) => (this.lastAccessDate = param),
            clearFilter: () => (this.lastAccessDate = null),
        });
    }

    public fromCamelCaseToSnakeCase(param: string): string {
        return param.replace(/([A-Z])/g, '_$1').toLowerCase();
    }
}
