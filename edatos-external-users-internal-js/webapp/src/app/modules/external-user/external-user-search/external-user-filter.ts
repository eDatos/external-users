import { DatePipe } from '@angular/common';
import { BaseEntityFilter, EntityFilter } from 'arte-ng/model';

export class ExternalUserFilter extends BaseEntityFilter implements EntityFilter {
    public query?: string;
    public includeDeleted: boolean;

    constructor(public datePipe?: DatePipe) {
        super(datePipe);
    }

    protected registerParameters() {
        this.registerParam({
            paramName: 'query',
            updateFilterFromParam: (param) => (this.query = param),
            clearFilter: () => (this.query = null),
        });
        this.registerParam({
            paramName: 'includeDeleted',
            updateFilterFromParam: (param) => {
                this.includeDeleted = param === 'true';
            },
            clearFilter: () => (this.includeDeleted = false),
        });
    }

    public getCriterias() {
        const criterias = [];
        if (this.query) {
            criterias.push(`QUERY ILIKE '%${this.escapeSingleQuotes(this.query)}%'`);
        }
        if (!this.includeDeleted) {
            criterias.push(`DELETION_DATE IS_NULL`);
        }
        return criterias;
    }
}
